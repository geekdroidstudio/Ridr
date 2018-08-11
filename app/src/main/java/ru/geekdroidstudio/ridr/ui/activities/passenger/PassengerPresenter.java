package ru.geekdroidstudio.ridr.ui.activities.passenger;

import android.location.Location;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekdroidstudio.ridr.model.communication.IPassengerCommunication;
import ru.geekdroidstudio.ridr.model.communication.entity.Driver;
import ru.geekdroidstudio.ridr.model.communication.entity.Passenger;
import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.communication.entity.UserAndRoute;
import ru.geekdroidstudio.ridr.model.communication.location.entity.Coordinate;
import ru.geekdroidstudio.ridr.model.communication.location.entity.DualCoordinateRoute;
import ru.geekdroidstudio.ridr.model.communication.location.entity.DualRoute;
import ru.geekdroidstudio.ridr.model.communication.location.entity.DualTextRoute;
import ru.geekdroidstudio.ridr.model.communication.request.entity.PassengerRequest;
import ru.geekdroidstudio.ridr.ui.activities.user.userbase.UserBasePresenter;
import timber.log.Timber;

@InjectViewState
public class PassengerPresenter extends UserBasePresenter<PassengerView> {


    @Inject
    IPassengerCommunication passengerCommunication;

    private Passenger passenger;

    private DualRoute dualRoute;

    public PassengerPresenter(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void setUserId(String id) {
        super.setUserId(id);

        passenger = new Passenger(id, "");

        compositeDisposable.add(startListenDrivers());
    }

    @Override
    public User getUser() {
        return passenger;
    }

    @Override
    protected void onLocationChanged(Location location) {
        Timber.d(location.toString());

        passenger.setLocation(new Coordinate(location.getLatitude(),
                location.getLongitude()));
        passengerCommunication.postLocation(passenger)
                .subscribe();

        getViewState().showPassengerOnMap(passenger);
    }

    @NonNull
    private Disposable startListenDrivers() {
        return passengerCommunication.getDriversObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(drivers -> {
                    Timber.d(drivers.toString());

                    List<UserAndRoute<? extends User>> driversAndRoutes = new ArrayList<>(drivers.size());
                    for (Driver driver : drivers) {
                        UserAndRoute<Driver> driverAndRoute = new UserAndRoute<>();
                        driverAndRoute.setUser(driver);
                        driversAndRoutes.add(driverAndRoute);
                    }

                    getViewState().showDriversOnMap(drivers);
                    getViewState().showDriversOnList(driversAndRoutes);
                }, Timber::e);
    }

    public void onRouteSelected(DualTextRoute dualTextRoute, List<LatLng> latLngArray) {
        dualRoute = new DualRoute();
        dualRoute.setTextRoute(dualTextRoute);
        dualRoute.setCoordinateRoute(new DualCoordinateRoute(latLngArray));
    }

    public void onItemClick(UserAndRoute<? extends User> userAndRoute) {
        if (dualRoute != null) {
            getViewState().showSendRequestDialog(userAndRoute);
        } else {
            getViewState().showNeedRouteMessage();
        }
    }

    public void onRouteSend(UserAndRoute<? extends User> userAndRoute) {
        PassengerRequest passengerRequest = new PassengerRequest();
        passengerRequest.setPassengerId(passenger.getId());
        passengerRequest.setDriverId(userAndRoute.getUser().getId());
        passengerRequest.setDualCoordinateRoute(dualRoute.getCoordinateRoute());

        compositeDisposable.add(postRequestStartResponseListener(passengerRequest));
    }

    @NonNull
    private Disposable postRequestStartResponseListener(PassengerRequest passengerRequest) {
        return passengerCommunication.postPassengerRequest(passengerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(driverResponse -> getViewState().showResponse(driverResponse),
                        Timber::e);
    }
}
