package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.model.communication.IDriverCommunication;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.routes.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.routes.DualCoordinateRoute;
import geekdroidstudio.ru.ridr.model.entity.routes.DualRoute;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
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

    private Map<String, UserAndRoute<Passenger>> passengersAndRoutes = new HashMap<>();

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
        //getViewState().showPassengersOnList(createPassengerAndRoute(1));
        //getViewState().showPassengersOnList(createPassengerAndRoute(3));
    }

    @NonNull
    private UserAndRoute<Passenger> createPassengerAndRoute(int id) {
        UserAndRoute<Passenger> passengerAndRoute = new UserAndRoute<>();
        passengerAndRoute.setUser(new Passenger("test" + id + "id", "test" + id + "name"));

        DualRoute dualRoute = new DualRoute();
        dualRoute.setCoordinateRoute(new DualCoordinateRoute(new Coordinate(id + 1.5, id + 1.6),
                new Coordinate(id + 3.5, id + 3.6)));
        dualRoute.setTextRoute(new DualTextRoute("start by " + id, "finish by " + id));
        passengerAndRoute.setDualRoute(dualRoute);
        return passengerAndRoute;
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

    @NonNull
    private Disposable startListenPassengersRequests(Driver driver) {
        return driverCommunication.getPassengerRequestObservable(driver.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(passengerRequest -> {
                    Timber.d(passengerRequest.toString());

                    //TODO: get passenger from list and go to viewState.showPassengerRequest

                    UserAndRoute<Passenger> passengerAndRoute = passengersAndRoutes
                            .get(passengerRequest.getPassengerId());

                    Passenger passenger = new Passenger(passengerRequest.getPassengerId(), "unknown");

                    DualRoute dualRoute = new DualRoute();
                    dualRoute.setCoordinateRoute(passengerRequest.getDualCoordinateRoute());

                    getViewState().showPassengerRequest(new UserAndRoute<>(passenger, dualRoute));
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

    public void onDriverResponse(Passenger passenger, boolean response) {
        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setDriverId(driver.getId());
        driverResponse.setPassengerId(passenger.getId());
        driverResponse.setAccept(response);

        driverCommunication.postPassengerResponse(driverResponse)
                .subscribe();

        if (response) {
            UserAndRoute<Passenger> passengerAndRoute = new UserAndRoute<>();
            passengerAndRoute.setUser(passenger);
            //passengerAndRoute.setDualRoute();//TODO

            passengersAndRoutes.put(passengerAndRoute.getUser().getId(), passengerAndRoute);
            getViewState().showPassengersOnList(new ArrayList<>(passengersAndRoutes.values()));
        }
    }
}
