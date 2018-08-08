package geekdroidstudio.ru.ridr.presenter;

import android.location.Location;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.communication.IPassengerCommunication;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.routes.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.routes.DualCoordinateRoute;
import geekdroidstudio.ru.ridr.model.entity.routes.DualRoute;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainView;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class PassengerMainPresenter extends UserBasePresenter<PassengerMainView> {


    @Inject
    IPassengerCommunication passengerCommunication;

    private Passenger passenger;

    private DualRoute dualRoute;

    public PassengerMainPresenter(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;

        compositeDisposable.add(startListenDrivers());
        //runRealGeo();
        runEmulateGeo();
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
