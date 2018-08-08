package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.presenter.PassengerMainPresenter;
import geekdroidstudio.ru.ridr.view.RouteSelectActivity;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.route_status.RouteStatusFragment;
import geekdroidstudio.ru.ridr.view.fragments.user_list.UserListFragment;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.FINISH_KEY;
import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.START_KEY;


public class PassengerMainActivity extends MvpAppCompatActivity implements PassengerMainView,
        MapFragment.OnFragmentInteractionListener,
        RouteStatusFragment.OnFragmentInteractionListener,
        UserListFragment.OnFragmentInteractionListener,
        AuthDatabase.IAuthDatabase {

    public static final int REQUEST_CODE_ROUTE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 333;

    @InjectPresenter
    PassengerMainPresenter passengerMainPresenter;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.route_status_fragment_tag)
    String routeStatusFragmentTag;

    @BindString(R.string.user_list_fragment_tag)
    String userListFragmentTag;

    @Inject
    AuthDatabase authDatabase;

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

        App.getInstance().getComponent().inject(this);
        String userId = getIntent().getStringExtra(UserMainActivity.USER_ID_KEY);
        Timber.d("onCreate: " + userId);

        authDatabase.setContext(this);
        authDatabase.getUserName(userId);

        String passengerId = userId;//getIntent().getStringExtra(PASSENGER_ID_KEY);
        String passengerName = "";//getIntent().getStringExtra(PASSENGER_NAME_KEY);

        passengerMainPresenter.setPassenger(new Passenger(passengerId, passengerName));

        mapFragment = (MapFragment) getFragment(mapFragmentTag);
        routeStatusFragment = (RouteStatusFragment) getFragment(routeStatusFragmentTag);
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
    public void showLocationSettingsError() {

    }

    @Override
    public void resolveLocationException(ApiException apiException) {
        try {
            ResolvableApiException resolvable = (ResolvableApiException) apiException;
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvable.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        } catch (ClassCastException e) {
            // Ignore, should be an impossible error.
        }
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
        passengerMainPresenter.onItemClick(userAndRoute);
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
                .setPositiveButton("Yes", (dialog, which) -> passengerMainPresenter.onRouteSend(userAndRoute))
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == Activity.RESULT_OK) {
                    passengerMainPresenter.locationErrorResolve();
                } else {
                    passengerMainPresenter.locationErrorNotResolve();
                }
                break;
            }
            case REQUEST_CODE_ROUTE: {
                if (resultCode == Activity.RESULT_OK) {
                    onRouteSelected(data);
                }
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

    @Override
    public void wasGetUserName(String userName) {
        Timber.d("wasGetUserName: " + userName);
    }
}
