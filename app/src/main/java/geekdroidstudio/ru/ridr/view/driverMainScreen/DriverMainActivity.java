package geekdroidstudio.ru.ridr.view.driverMainScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.view.adapters.DriverRecyclerViewAdapter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static android.widget.LinearLayout.VERTICAL;

public class DriverMainActivity extends MvpAppCompatActivity implements DriverMainView,
        MapFragment.OnFragmentInteractionListener {
    private static final int REQUEST_CHECK_SETTINGS = 333;
    @BindView(R.id.holder_recycler_view_objects_around)
    RecyclerView rvPassengerList;

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
        ButterKnife.bind(this);
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
    private Fragment getMapFragment() {
        return getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
    }
}
