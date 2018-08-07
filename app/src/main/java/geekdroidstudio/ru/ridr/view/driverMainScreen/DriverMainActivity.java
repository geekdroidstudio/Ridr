package geekdroidstudio.ru.ridr.view.driverMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.routes.DualCoordinateRoute;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.user_list.UserListFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DriverMainActivity extends MvpAppCompatActivity implements DriverMainView,
        MapFragment.OnFragmentInteractionListener, UserListFragment.OnFragmentInteractionListener {

    public static final String USER_ID_KEY = "userIdKey";
    public static final String USER_NAME_KEY = "userNameKey";
    //TODO: объединить где-нибудь вместе с PassengerMainActivity ключами(в AuthActivity)

    @InjectPresenter
    DriverMainPresenter driverMainPresenter;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.user_list_fragment_tag)
    String userListFragmentTag;

    private MapFragment mapFragment;
    private UserListFragment userListFragment;

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
    public void showPassengerRequest(Passenger passenger, DualCoordinateRoute dualCoordinateRoute) {
        String msg = passenger.getName() + " request " + dualCoordinateRoute.toString();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addPassenger(UserAndRoute<Passenger> userAndRoute) {
        userListFragment.addUserAndRoute(userAndRoute);
    }

    @Override
    public void onItemClick(UserAndRoute<? extends User> userAndRoute) {
        Toast.makeText(this, "onClick " + userAndRoute.getUser().getName(), Toast.LENGTH_SHORT).show();
    }

    @Nullable
    private Fragment getMapFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
