package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.model.communication.IDriverCommunication;
import geekdroidstudio.ru.ridr.model.entity.communication.SimpleRoute;
import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainView;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class DriverMainPresenter extends MvpPresenter<DriverMainView> {

    @Inject
    Repository repository;

    @Inject
    IDriverCommunication driverCommunication;

    private Driver driver;

    private Scheduler scheduler;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DriverMainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void setDriver(Driver driver) {
        this.driver = driver;

        compositeDisposable.add(startListenPassengersLocation());
        compositeDisposable.add(startListenPassengersRequests(driver));

        startListenGeo();


        //TODO: debug
        UserAndRoute<Passenger> passengerAndRoute = new UserAndRoute<>();
        passengerAndRoute.setUser(new Passenger("test1id", "test1name"));
        passengerAndRoute.setSimpleRoute(new SimpleRoute(new Coordinate(1.5, 2.6),
                new Coordinate(0.5, 1.6)));
        getViewState().addPassenger(passengerAndRoute);
    }

    @NonNull
    private Disposable startListenPassengersRequests(Driver driver) {
        return driverCommunication.getPassengerRequestObservable(driver.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(passengerRequest -> {
                    Timber.d(passengerRequest.toString());

                    //TODO: get passenger from list and go to viewState.showPassengerRequest
                }, Timber::e);
    }

    @NonNull
    private Disposable startListenPassengersLocation() {
        return driverCommunication.getPassengersObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(passengers -> {
                    Timber.d(passengers.toString());

                    getViewState().showPassengersOnMap(passengers);
                }, Timber::e);
    }

    @SuppressLint("CheckResult")
    private void startListenGeo() {
        repository.startListenLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(location -> {
                    Timber.d(location.toString());

                    driver.setLocation(new Coordinate(location.getLatitude(),
                            location.getLongitude()));
                    driverCommunication.postLocation(driver);

                    getViewState().showDriverOnMap(driver);
                }, Timber::e);
    }
}
