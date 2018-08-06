package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainView;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class DriverMainPresenter extends MvpPresenter<DriverMainView> {

    @Inject
    Repository repository;

    private Scheduler scheduler;

    public DriverMainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showMapFragment();
        getViewState().showDriverRecycler();

        startListenGeo();
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
