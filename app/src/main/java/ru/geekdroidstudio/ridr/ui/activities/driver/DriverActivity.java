package ru.geekdroidstudio.ridr.ui.activities.driver;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekdroidstudio.ridr.App;
import ru.geekdroidstudio.ridr.R;
import ru.geekdroidstudio.ridr.model.communication.entity.Passenger;
import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.communication.entity.UserAndRoute;
import ru.geekdroidstudio.ridr.ui.activities.userbase.UserBaseActivity;
import ru.geekdroidstudio.ridr.ui.fragments.map.MapFragment;
import ru.geekdroidstudio.ridr.ui.fragments.userlist.UserListFragment;

public class DriverActivity extends UserBaseActivity<DriverPresenter> implements DriverView,
        MapFragment.OnFragmentInteractionListener, UserListFragment.OnFragmentInteractionListener {

    @InjectPresenter
    DriverPresenter presenter;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.user_list_fragment_tag)
    String userListFragmentTag;

    private MapFragment mapFragment;
    private UserListFragment userListFragment;
    private AlertDialog alertDialog;

    public DriverActivity() {
        App.getInstance().getComponent().inject(this);
    }

    @Override
    public DriverPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    public DriverPresenter providePresenter() {
        DriverPresenter presenter = new DriverPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        ButterKnife.bind(this);

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
