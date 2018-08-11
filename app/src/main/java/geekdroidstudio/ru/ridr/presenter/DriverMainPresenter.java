package geekdroidstudio.ru.ridr.presenter;

import android.location.Location;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.communication.IDriverCommunication;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.routes.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.routes.DualRoute;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainView;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class DriverMainPresenter extends UserBasePresenter<DriverMainView> {

    @Inject
    IDriverCommunication driverCommunication;

    private Driver driver;

    private Map<String, UserAndRoute<Passenger>> passengersAndRoutes = new HashMap<>();

    private List<Passenger> passengers = new ArrayList<>();

    public DriverMainPresenter(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void setUserId(String id) {
        super.setUserId(id);

        driver = new Driver(id, "");

        compositeDisposable.add(startListenPassengersLocation());
        compositeDisposable.add(startListenPassengersRequests(driver));
    }

    @Override
    public User getUser() {
        return driver;
    }

    @Override
    protected void onLocationChanged(Location location) {
        Timber.d(location.toString());

        driver.setLocation(new Coordinate(location.getLatitude(),
                location.getLongitude()));
        driverCommunication.postLocation(driver)
                .subscribe();

        getViewState().showDriverOnMap(driver);
    }

    @NonNull
    private Disposable startListenPassengersLocation() {
        return driverCommunication.getPassengersObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(passengers -> {
                    Timber.d(passengers.toString());

                    this.passengers = passengers;
                    getViewState().showPassengersOnMap(passengers);
                }, Timber::e);
    }

    @NonNull
    private Disposable startListenPassengersRequests(Driver driver) {
        return driverCommunication.getPassengerRequestObservable(driver.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(getPassengerRequestConsumer(), Timber::e);
    }

    @NonNull
    private Consumer<PassengerRequest> getPassengerRequestConsumer() {
        return passengerRequest -> {
            Timber.d(passengerRequest.toString());

            for (Passenger passenger : passengers) {
                if (passenger.getId().equals(passengerRequest.getPassengerId())) {
                    DualRoute dualRoute = new DualRoute();
                    dualRoute.setCoordinateRoute(passengerRequest.getDualCoordinateRoute());

                    getViewState().showPassengerRequest(new UserAndRoute<>(passenger, dualRoute));
                    return;
                }
            }

            Passenger passenger = new Passenger(passengerRequest.getPassengerId(), "");
            sendDriverResponse(passenger, false);
        };
    }

    public void onDriverResponse(Passenger passenger, boolean response) {
        sendDriverResponse(passenger, response);

        if (response) {
            UserAndRoute<Passenger> passengerAndRoute = new UserAndRoute<>();
            passengerAndRoute.setUser(passenger);
            //passengerAndRoute.setDualRoute();//TODO

            passengersAndRoutes.put(passengerAndRoute.getUser().getId(), passengerAndRoute);
            getViewState().showPassengersOnList(new ArrayList<>(passengersAndRoutes.values()));
        }
    }

    private void sendDriverResponse(Passenger passenger, boolean response) {
        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setDriverId(driver.getId());
        driverResponse.setPassengerId(passenger.getId());
        driverResponse.setAccept(response);

        driverCommunication.postPassengerResponse(driverResponse)
                .subscribe();
    }
}