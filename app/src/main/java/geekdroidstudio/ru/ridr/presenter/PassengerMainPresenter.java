package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.server.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainView;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class PassengerMainPresenter extends MvpPresenter<PassengerMainView> {

    @Inject
    Repository repository;

    @Inject
    AuthDatabase authDatabase;

    private Scheduler scheduler;

    private Passenger passenger;

    public PassengerMainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        //  getViewState().showFindDriversFragment();
        getViewState().showMapFragment();
        getViewState().showRouteDataFragment();

        startListenGeo();
    }

    public void setPassengerId(Context context, String id) {
        authDatabase.setContext(context);

        passenger = new Passenger(id, "need_load_name_from_auth_db");//TODO
        //String name = authDatabase.getUserName(id);

    }

    @SuppressLint("CheckResult")
    private void startListenGeo() {
        repository
                .startListenLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(location -> Timber.d(String.valueOf(location.getLatitude())), Timber::e);
    }

    public void routeCreated(List<LatLng> routePoints) {
        getViewState().showRouteInMapFragment(routePoints);
    }
}
