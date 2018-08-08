package geekdroidstudio.ru.ridr.view.driverMainScreen;

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
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.view.UserBaseActivity;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.user_list.UserListFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity.USER_ID_KEY;

public class DriverMainActivity extends UserBaseActivity<DriverMainPresenter> implements DriverMainView,
        MapFragment.OnFragmentInteractionListener, UserListFragment.OnFragmentInteractionListener {

    @InjectPresenter
    DriverMainPresenter presenter;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.user_list_fragment_tag)
    String userListFragmentTag;

    private MapFragment mapFragment;
    private UserListFragment userListFragment;
    private AlertDialog alertDialog;

    public DriverMainActivity() {
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public DriverMainPresenter getPresenter() {
        return presenter;
    }

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

        if (savedInstanceState == null) {
            String userId = getIntent().getStringExtra(USER_ID_KEY);

            loadUserName(userId);

            presenter.setDriver(new Driver(userId, ""));
        }

        mapFragment = (MapFragment) getFragment(mapFragmentTag);
        userListFragment = (UserListFragment) getFragment(userListFragmentTag);
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
        presenter.onDriverResponse(passengerAndRoute.getUser(), response);
    }

    @Override
    public void onItemClick(UserAndRoute<? extends User> userAndRoute) {
        Toast.makeText(this, "onClick " + userAndRoute.getUser().getName(), Toast.LENGTH_SHORT).show();
    }
}
