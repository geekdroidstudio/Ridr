package geekdroidstudio.ru.ridr.view.driverMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.di.AppComponent;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.server.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.view.adapters.DriverRecyclerViewAdapter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static android.widget.LinearLayout.VERTICAL;

public class DriverMainActivity extends MvpAppCompatActivity implements DriverMainView,
        MapFragment.OnFragmentInteractionListener, AuthDatabase.IAuthDatabase {
    @BindView(R.id.holder_recycler_view_objects_around)
    RecyclerView rvPassengerList;

    @InjectPresenter
    DriverMainPresenter driverMainPresenter;

    @Inject AuthDatabase authDatabase;

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
        String userId = getIntent().getStringExtra(UserMainActivity.USER_ID_KEY);
        Timber.d("onCreate: " + userId);
        App.getInstance().getComponent().inject(this);
        authDatabase.setContext(this);
        authDatabase.getUserName(userId);


    }

    @Override
    public void showMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_driver_map_frame, MapFragment.newInstance(),
                        MapFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRouteInMapFragment(List<LatLng> routePoints) {
        Fragment fragment = getMapFragment();
        if (fragment != null) {
            ((MapFragment) fragment).showRoute(routePoints);
        }
    }

    @Override
    public void showUserInMapFragment(User user) {
        Fragment fragment = getMapFragment();
        if (fragment != null) {
            ((MapFragment) fragment).showUser(user);
        }
    }

    @Override
    public void showMapObjectsInMapFragment(List<User> users) {
        Fragment fragment = getMapFragment();
        if (fragment != null) {
            ((MapFragment) fragment).showMapObjects(users);
        }
    }

    @Override
    public void showDriverRecycler() {
        rvPassengerList.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        rvPassengerList.setAdapter(new DriverRecyclerViewAdapter());
    }

    @Nullable
    private Fragment getMapFragment() {
        return getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
    }

    @Override
    public void wasGetUserName(String userName) {
        Timber.d("wasGetUserName: " + userName);
    }
}
