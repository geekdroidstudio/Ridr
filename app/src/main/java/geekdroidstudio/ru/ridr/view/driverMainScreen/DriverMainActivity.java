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

import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.view.adapters.DriverRecyclerItemViewHolder;
import geekdroidstudio.ru.ridr.view.adapters.DriverRecyclerViewAdapter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.widget.LinearLayout.VERTICAL;

public class DriverMainActivity extends MvpAppCompatActivity implements DriverMainView,
        MapFragment.OnFragmentInteractionListener {

    @InjectPresenter
    DriverMainPresenter driverMainPresenter;

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

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    @Override
    public void showRouteInMapFragment(List<LatLng> routePoints) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
        if (fragment != null) {
            ((MapFragment) fragment).showRoute(routePoints);
        }
    }

    @Override
    public void showUserInMapFragment(User user) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
        if (fragment != null) {
            ((MapFragment) fragment).showUser(user);
        }
    }

    @Override
    public void showMapObjectsInMapFragment(List<User> users) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
        if (fragment != null) {
            ((MapFragment) fragment).showMapObjects(users);
        }
    }

    @Override
    public void showDriverRecycler() {
        RecyclerView.Adapter<DriverRecyclerItemViewHolder> adapter;
        RecyclerView rvPassengerList = findViewById(R.id.holder_recycler_view_objects_around);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(VERTICAL);
        rvPassengerList.setLayoutManager(layoutManager);
        adapter = new DriverRecyclerViewAdapter();
        rvPassengerList.setAdapter(adapter);
    }

    @Nullable
    private Fragment getMapFragment() {
        return getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
    }
}
