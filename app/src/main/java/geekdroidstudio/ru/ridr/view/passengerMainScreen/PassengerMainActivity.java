package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.PassengerMainPresenter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment;
import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataFragment;

public class PassengerMainActivity extends MvpAppCompatActivity implements PassengerMainView,
        MapFragment.OnFragmentInteractionListener, RouteDataFragment.OnFragmentInteractionListener,
        PassengerFindDriversFragment.OnFragmentInteractionListener {
    @InjectPresenter
    PassengerMainPresenter passengerMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);
    }

    @Override
    public void showFindDriversFragment() {
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_passenger_main_frame, PassengerFindDriversFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();*/
    }

    @Override
    public void openDriverRouteData(int id) {
        Toast.makeText(this,"Вы выбрали маршрут " + id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void routeCreated(List<LatLng> routePoints) {

    }

    @Override
    public void hideKeyboard(IBinder windowToken) {

    }

    /*@Override
    public void showRouteDataFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_main_frame, RouteDataFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }*/
}
