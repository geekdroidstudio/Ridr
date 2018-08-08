package geekdroidstudio.ru.ridr.view.driverMainScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.user_list.UserListFragment;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity.USER_ID_KEY;

public class DriverMainActivity extends MvpAppCompatActivity implements DriverMainView,
        MapFragment.OnFragmentInteractionListener, UserListFragment.OnFragmentInteractionListener {

    private static final int REQUEST_CHECK_SETTINGS = 333;
    //TODO: объединить где-нибудь вместе с PassengerMainActivity ключами(в AuthActivity)

    @InjectPresenter
    DriverMainPresenter driverMainPresenter;

    @Inject
    AuthDatabase authDatabase;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.user_list_fragment_tag)
    String userListFragmentTag;

    private MapFragment mapFragment;
    private UserListFragment userListFragment;
    private AlertDialog alertDialog;

    @ProvidePresenter
    public DriverMainPresenter providePresenter() {
        DriverMainPresenter presenter = new DriverMainPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        ButterKnife.bind(this);
        String userId = getIntent().getStringExtra(USER_ID_KEY);
        Timber.d("onCreate: " + userId);
        App.getInstance().getComponent().inject(this);
        authDatabase.setContext(this);
        authDatabase.getUserName(userId);

        if (savedInstanceState == null) {
            String driverId = getIntent().getStringExtra(USER_ID_KEY);
            String driverName = getIntent().getStringExtra(USER_NAME_KEY);

            //TODO: убрать при передаче настоящих значений
            driverId = "test2id";
            driverName = "test2name";

            driverMainPresenter.setDriver(new Driver(driverId, driverName));
        }

        mapFragment = (MapFragment) getMapFragment(mapFragmentTag);
        userListFragment = (UserListFragment) getMapFragment(userListFragmentTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.hide();
        }
    }

    @Override
    public void showRouteOnMap(List<LatLng> routePoints) {
        mapFragment.showRoute(routePoints);
    }

    @Override
    public void showDriverOnMap(User user) {
        mapFragment.showUser(user);
    }

    @Override
    public void showPassengersOnMap(List<? extends User> users) {
        mapFragment.showUsersOnMap(users);
    }

    @Override
    public void showPassengersOnList(List<UserAndRoute<? extends User>> passengersAndRoutes) {
        userListFragment.setUsersAndRoutes(passengersAndRoutes);
    }

    @Override
    public void showPassengerRequest(UserAndRoute<Passenger> passengerAndRoute) {
        final String message = passengerAndRoute.getUser().getName()
                + " request: " + passengerAndRoute.getDualRoute().getCoordinateRoute();
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setNegativeButton("Reject",
                        (dialog, which) -> postDriverResponse(passengerAndRoute, false))
                .setPositiveButton("Accept",
                        (dialog, which) -> postDriverResponse(passengerAndRoute, true))
                .setCancelable(false)
                .create();

        alertDialog.show();
    }

    private void postDriverResponse(UserAndRoute<Passenger> passengerAndRoute, boolean response) {
        driverMainPresenter.onDriverResponse(passengerAndRoute.getUser(), response);
    }

    @Override
    public void onItemClick(UserAndRoute<? extends User> userAndRoute) {
        Toast.makeText(this, "onClick " + userAndRoute.getUser().getName(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void resolveLocationException(ApiException exception) {
        try {
            ResolvableApiException resolvable = (ResolvableApiException) exception;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == Activity.RESULT_OK) {
                    driverMainPresenter.locationErrorResolve();
                } else {
                    driverMainPresenter.locationErrorNotResolve();
                }
                break;
            }
            default: {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void showLocationSettingsError() {

    }

    @Nullable
    private Fragment getMapFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public void wasGetUserName(String userName) {
        Timber.d("wasGetUserName: " + userName);
    }
}
