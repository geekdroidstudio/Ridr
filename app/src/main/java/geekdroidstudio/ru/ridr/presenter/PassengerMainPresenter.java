package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.model.communication.IPassengerCommunication;
import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainView;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class PassengerMainPresenter extends MvpPresenter<PassengerMainView> {

    @Inject
    Repository repository;

    @Inject
    IPassengerCommunication passengerCommunication;

    private Scheduler scheduler;

    private Passenger passenger;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PassengerMainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        checkLocationServices();
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

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;

        compositeDisposable.add(startListenGeo());
        compositeDisposable.add(startListenDrivers());
    }

    @NonNull
    private Disposable startListenDrivers() {
        return passengerCommunication.getDriversObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(drivers -> {
                    Timber.d(drivers.toString());

                    getViewState().showDriversOnMap(drivers);
                }, Timber::e);
    }

    private Disposable startListenGeo() {
        return repository.startListenLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(location -> {
                    Timber.d(location.toString());

                    passenger.setLocation(new Coordinate(location.getLatitude(),
                            location.getLongitude()));
                    passengerCommunication.postLocation(passenger);

                    getViewState().showPassengerOnMap(passenger);
                }, Timber::e);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public void locationErrorResolve() {
        startListenGeo();
    }

    public void locationErrorNotResolve() {
        getViewState().showLocationSettingsError();
    }
}
