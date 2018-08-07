package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainView;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@InjectViewState
public class DriverMainPresenter extends MvpPresenter<DriverMainView> {

    @Inject
    Repository repository;

    private Scheduler scheduler;
    private Disposable geoListenDisposable;

    public DriverMainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showMapFragment();
        getViewState().showDriverRecycler();
        checkLocationServices();
    }

    @SuppressLint("CheckResult")
    private void startListenGeo() {
        geoListenDisposable = repository
                .startListenLocation()
                .subscribe(location -> Timber.d(String.valueOf(location.getLatitude())), Timber::e);
    }

    public void routeCreated(List<LatLng> routePoints) {
        getViewState().showRouteInMapFragment(routePoints);
    }

    @SuppressLint("CheckResult")
    private void checkLocationServices() {
        repository
                .checkLocationResponse()
                .subscribe(this::startListenGeo
                        , throwable -> {
                            if (!(throwable instanceof ApiException)) {
                                getViewState().showLocationSettingsError();
                                Timber.e(throwable);
                                return;
                            }
                            ApiException apiException = (ApiException) throwable;
                            if (apiException.getStatusCode() != LocationSettingsStatusCodes
                                    .RESOLUTION_REQUIRED) {
                                getViewState().showLocationSettingsError();
                                Timber.e(throwable);
                                return;
                            }
                            getViewState().resolveLocationException(apiException);
                        });
    }

    public void locationErrorResolve() {
        startListenGeo();
    }

    public void locationErrorNotResolve() {
        getViewState().showLocationSettingsError();
    }
}