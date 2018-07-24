package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.PassMainActivityPresenter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapView;
import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataFragment;

public class PassMainActivity extends MvpAppCompatActivity implements PassMainView,
        MapFragment.OnFragmentInteractionListener, RouteDataFragment.OnFragmentInteractionListener {
    @InjectPresenter
    PassMainActivityPresenter passMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_main);
    }

    @Override
    public void showMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_main_map_container, MapFragment.newInstance(),
                        MapFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRouteDataFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_main_frame, RouteDataFragment.newInstance(),
                        RouteDataFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRouteInMapFragment(List<LatLng> routePoints) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
        if (fragment != null && fragment instanceof MapView) {
            ((MapView) fragment).showRoute(routePoints);
        }
    }

    @Override
    public void routeCreated(List<LatLng> routePoints) {
        passMainPresenter.routeCreated(routePoints);
    }

    @Override
    public void hideKeyboard(IBinder windowToken) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null) {
            in.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
