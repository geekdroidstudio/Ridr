package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.presenter.PassengerMainPresenter;
import geekdroidstudio.ru.ridr.view.RouteSelectActivity;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.route_status.RouteStatusFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.FINISH_KEY;
import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.ROUTE_KEY;
import static geekdroidstudio.ru.ridr.view.RouteSelectActivity.START_KEY;


public class PassengerMainActivity extends MvpAppCompatActivity implements PassengerMainView,
        MapFragment.OnFragmentInteractionListener,
        RouteStatusFragment.OnFragmentInteractionListener {

    public static final String PASSENGER_ID_KEY = "passengerIdKey";
    public static final String PASSENGER_NAME_KEY = "passengerNameKey";

    public static final int REQUEST_CODE_ROUTE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 333;

    @InjectPresenter
    PassengerMainPresenter passengerMainPresenter;

    @BindString(R.string.map_fragment_tag)
    String mapFragmentTag;

    @BindString(R.string.route_status_fragment_tag)
    String routeStatusFragmentTag;

    MapFragment mapFragment;
    RouteStatusFragment routeStatusFragment;

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

        ButterKnife.bind(this);

        String passengerId = getIntent().getStringExtra(PASSENGER_ID_KEY);
        String passengerName = getIntent().getStringExtra(PASSENGER_NAME_KEY);

        //TODO: убрать при передаче настоящих значений
        passengerId = "test1id";
        passengerName = "test1name";

        passengerMainPresenter.setPassenger(new Passenger(passengerId, passengerName));

        mapFragment = (MapFragment) getFragment(mapFragmentTag);
        routeStatusFragment = (RouteStatusFragment) getFragment(routeStatusFragmentTag);
    }

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    @Override
    public void showRouteInMapFragment(List<LatLng> routePoints) {
        mapFragment.showRoute(routePoints);
    }

    @Override
    public void showPassengerOnMap(User user) {
        mapFragment.showUser(user);
    }

    @Override
    public void showDriversOnMap(List<? extends User> users) {
        mapFragment.showMapObjects(users);
    }

    @Override
    public void showLocationSettingsError() {

    }

    @Override
    public void resolveLocationException(ApiException apiException) {
        try {
            ResolvableApiException resolvable = (ResolvableApiException) apiException;
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
    public void goRouteChange(String start, String finish) {
        Intent intent = new Intent(this, RouteSelectActivity.class);

        intent.putExtra(START_KEY, start);
        intent.putExtra(FINISH_KEY, finish);

        startActivityForResult(intent, REQUEST_CODE_ROUTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == Activity.RESULT_OK) {
                    passengerMainPresenter.locationErrorResolve();
                } else {
                    passengerMainPresenter.locationErrorNotResolve();
                }
                break;
            }
            case REQUEST_CODE_ROUTE: {
                if (resultCode == Activity.RESULT_OK) {
                    String start = data.getStringExtra(START_KEY);
                    String finish = data.getStringExtra(FINISH_KEY);
                    List<LatLng> latLngArray = data.getParcelableArrayListExtra(ROUTE_KEY);

                    routeStatusFragment.onRouteSelected(start, finish);
                    mapFragment.showRoute(latLngArray);
                } else {
                    //do handle error result
                }
                break;
            }
        }
    }


    private Fragment getFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
