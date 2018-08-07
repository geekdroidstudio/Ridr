package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.presenter.PassengerMainPresenter;
import geekdroidstudio.ru.ridr.view.RouteSelectActivity;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.route_status.RouteStatusFragment;
import geekdroidstudio.ru.ridr.view.fragments.user_list.UserListFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.FINISH_KEY;
import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.MULTI_ROUTE_KEY;
import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.START_KEY;


public class PassengerMainActivity extends MvpAppCompatActivity implements PassengerMainView,
        MapFragment.OnFragmentInteractionListener,
        RouteStatusFragment.OnFragmentInteractionListener,
        UserListFragment.OnFragmentInteractionListener {

    public static final String USER_ID_KEY = "userIdKey";
    public static final String USER_NAME_KEY = "userNameKey";

    public static final int REQUEST_CODE_ROUTE = 1;

    @InjectPresenter
    PassengerMainPresenter passengerMainPresenter;

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

    @ProvidePresenter
    public PassengerMainPresenter providePresenter() {
        PassengerMainPresenter presenter = new PassengerMainPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        ButterKnife.bind(this);

        mapFragment = (MapFragment) getFragment(mapFragmentTag);
        routeStatusFragment = (RouteStatusFragment) getFragment(routeStatusFragmentTag);
        userListFragment = (UserListFragment) getFragment(userListFragmentTag);

        if (savedInstanceState == null) {
            String passengerId = getIntent().getStringExtra(USER_ID_KEY);
            String passengerName = getIntent().getStringExtra(USER_NAME_KEY);

            //TODO: убрать при передаче настоящих значений
            passengerId = "test1id";
            passengerName = "test1name";

            passengerMainPresenter.setPassenger(new Passenger(passengerId, passengerName));

            //TODO:debug
            ArrayList<LatLng> list = new ArrayList<>();

            for (int i = 1; i < 10; i++) {
                list.add(new LatLng(i, i));
            }

            Intent intent = new Intent();
            intent.putExtra(START_KEY, "startTest");
            intent.putExtra(FINISH_KEY, "finishTest");
            intent.putParcelableArrayListExtra(MULTI_ROUTE_KEY, list);

            onRouteSelected(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alertDialog.hide();
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
    public void goRouteChange(DualTextRoute dualTextRoute) {
        Intent intent = new Intent(this, RouteSelectActivity.class);

        intent.putExtra(START_KEY, dualTextRoute.getStart());
        intent.putExtra(FINISH_KEY, dualTextRoute.getFinish());

        startActivityForResult(intent, REQUEST_CODE_ROUTE);
    }

    @Override
    public void addDriver(UserAndRoute<Driver> userAndRoute) {
        userListFragment.addUserAndRoute(userAndRoute);
    }

    @Override
    public void onItemClick(UserAndRoute<? extends User> userAndRoute) {
        passengerMainPresenter.onItemClick(userAndRoute);
    }

    @Override
    public void showNeedRouteMessage() {
        Toast.makeText(this, "Need select route", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendRequestDialog(UserAndRoute<? extends User> userAndRoute) {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("Send message to " + userAndRoute.getUser().getName())
                .setNegativeButton("No", (dialog, which) -> {
                })
                .setPositiveButton("Yes", (dialog, which) -> passengerMainPresenter.onRouteSend(userAndRoute))
                .create();

        alertDialog.show();
    }

    @Override
    public void showResponse(DriverResponse driverResponse) {
        if (driverResponse.getAccept()) {
            final String message = driverResponse.getDriverId() + " accepted request";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ROUTE:
                    onRouteSelected(data);
                    break;
            }
        }
    }

    private void onRouteSelected(Intent data) {
        String start = data.getStringExtra(START_KEY);
        String finish = data.getStringExtra(FINISH_KEY);
        DualTextRoute dualTextRoute = new DualTextRoute(start, finish);
        List<LatLng> latLngArray = data.getParcelableArrayListExtra(MULTI_ROUTE_KEY);

        passengerMainPresenter.onRouteSelected(dualTextRoute, latLngArray);
        routeStatusFragment.onRouteSelected(dualTextRoute);
        mapFragment.showRoute(latLngArray);
    }

    private Fragment getFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
