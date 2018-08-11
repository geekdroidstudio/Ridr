package geekdroidstudio.ru.ridr.ui.activities.passenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.ui.activities.routeselect.RouteSelectActivity;
import geekdroidstudio.ru.ridr.ui.activities.userbase.UserBaseActivity;
import geekdroidstudio.ru.ridr.ui.fragments.map.MapFragment;
import geekdroidstudio.ru.ridr.ui.fragments.routestatus.RouteStatusFragment;
import geekdroidstudio.ru.ridr.ui.fragments.userlist.UserListFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static geekdroidstudio.ru.ridr.ui.activities.routeselect.RouteSelectActivity.FINISH_KEY;
import static geekdroidstudio.ru.ridr.ui.activities.routeselect.RouteSelectActivity.MULTI_ROUTE_KEY;
import static geekdroidstudio.ru.ridr.ui.activities.routeselect.RouteSelectActivity.START_KEY;


public class PassengerActivity extends UserBaseActivity<PassengerPresenter> implements
        PassengerView,
        MapFragment.OnFragmentInteractionListener,
        RouteStatusFragment.OnFragmentInteractionListener,
        UserListFragment.OnFragmentInteractionListener {

    public static final int REQUEST_CODE_ROUTE = 1;

    @InjectPresenter
    PassengerPresenter presenter;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.route_status_fragment_tag)
    String routeStatusFragmentTag;

    @BindString(R.string.user_list_fragment_tag)
    String userListFragmentTag;

    private MapFragment mapFragment;
    private RouteStatusFragment routeStatusFragment;
    private UserListFragment userListFragment;
    private AlertDialog alertDialog;

    public PassengerActivity() {
        App.getInstance().getComponent().inject(this);
    }

    @ProvidePresenter
    public PassengerPresenter providePresenter() {
        PassengerPresenter presenter = new PassengerPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getComponent().inject(presenter);
        return presenter;
    }

    @Override
    public PassengerPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        ButterKnife.bind(this);

        mapFragment = (MapFragment) getFragment(mapFragmentTag);
        routeStatusFragment = (RouteStatusFragment) getFragment(routeStatusFragmentTag);
        userListFragment = (UserListFragment) getFragment(userListFragmentTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.hide();
        }
    }

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    @Override
    public void showRouteOnMap(List<LatLng> routePoints) {
        mapFragment.showRoute(routePoints);
    }

    @Override
    public void showPassengerOnMap(User user) {
        mapFragment.showUser(user);
    }

    @Override
    public void showDriversOnMap(List<? extends User> users) {
        mapFragment.showUsersOnMap(users);
    }

    @Override
    public void showDriversOnList(List<UserAndRoute<? extends User>> driversAndRoutes) {

        userListFragment.setUsersAndRoutes(driversAndRoutes);
    }

    @Override
    public void goRouteChange(DualTextRoute dualTextRoute) {
        Intent intent = new Intent(this, RouteSelectActivity.class);

        intent.putExtra(START_KEY, "");//dualTextRoute.getStart());
        intent.putExtra(FINISH_KEY, "");//dualTextRoute.getFinish());

        startActivityForResult(intent, REQUEST_CODE_ROUTE);
    }

    @Override
    public void onItemClick(UserAndRoute<? extends User> userAndRoute) {
        presenter.onItemClick(userAndRoute);
    }

    @Override
    public void showNeedRouteMessage() {
        Toast.makeText(this, "Need select route", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendRequestDialog(UserAndRoute<? extends User> userAndRoute) {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("Send request to " + userAndRoute.getUser().getName())
                .setNegativeButton("No", (dialog, which) -> {
                })
                .setPositiveButton("Yes", (dialog, which) -> presenter.onRouteSend(userAndRoute))
                .create();

        alertDialog.show();
    }

    @Override
    public void showResponse(DriverResponse driverResponse) {
        final String message;
        if (driverResponse.getAccept()) {
            message = driverResponse.getDriverId() + " accepted request";
        } else {
            message = driverResponse.getDriverId() + " rejected request";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ROUTE: {
                if (resultCode == Activity.RESULT_OK) {
                    onRouteSelected(data);
                }
                break;
            }
            default: {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void onRouteSelected(Intent data) {
        String start = data.getStringExtra(START_KEY);
        String finish = data.getStringExtra(FINISH_KEY);
        DualTextRoute dualTextRoute = new DualTextRoute(start, finish);
        List<LatLng> latLngArray = data.getParcelableArrayListExtra(MULTI_ROUTE_KEY);

        presenter.onRouteSelected(dualTextRoute, latLngArray);
        routeStatusFragment.onRouteSelected(dualTextRoute);
        mapFragment.showRoute(latLngArray);
    }
}
