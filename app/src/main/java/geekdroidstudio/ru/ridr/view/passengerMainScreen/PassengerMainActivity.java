package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.presenter.PassengerMainPresenter;
import geekdroidstudio.ru.ridr.server.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversFragment;
import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataFragment;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class PassengerMainActivity extends MvpAppCompatActivity implements PassengerMainView,
        MapFragment.OnFragmentInteractionListener, RouteDataFragment.OnFragmentInteractionListener,
        PassengerFindDriversFragment.OnFragmentInteractionListener, AuthDatabase.IAuthDatabase {
    @InjectPresenter
    PassengerMainPresenter passengerMainPresenter;

    @Inject
    AuthDatabase authDatabase;

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

        App.getInstance().getComponent().inject(this);

        String userId = getIntent().getStringExtra(UserMainActivity.USER_ID_KEY);

        //получаем id пользователя
        Timber.d("PassengerActivity: onCreate: " + userId);
        authDatabase.setContext(this);
        authDatabase.getUserName(userId);

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
    public void showMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_passenger_map_frame, MapFragment.newInstance(),
                        MapFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRouteDataFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_passenger_route_frame, RouteDataFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void openDriverRouteData(int id) {
        Toast.makeText(this, "Вы выбрали маршрут " + id, Toast.LENGTH_SHORT).show();
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
    public void routeCreated(List<LatLng> routePoints) {
        passengerMainPresenter.routeCreated(routePoints);
    }

    @Override
    public void hideKeyboard(IBinder windowToken) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null) {
            in.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    @Nullable
    private Fragment getMapFragment() {
        return getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
    }

    @Override
    public void getUserName(String userName) {
        //получаем имя пользователя
        Timber.d("Passenger: getUserName: " + userName);
    }
}
