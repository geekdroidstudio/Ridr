package ru.geekdroidstudio.ridr.model.communication;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.geekdroidstudio.ridr.model.communication.entity.Driver;
import ru.geekdroidstudio.ridr.model.communication.entity.Passenger;
import ru.geekdroidstudio.ridr.model.communication.location.IUserLocationRepository;
import ru.geekdroidstudio.ridr.model.communication.request.IPassengerRequestRepository;
import ru.geekdroidstudio.ridr.model.communication.request.entity.DriverResponse;
import ru.geekdroidstudio.ridr.model.communication.request.entity.PassengerRequest;

public class DriverCommunication extends UserCommunication<Passenger>
        implements IDriverCommunication {

    private IPassengerRequestRepository passengerRequestRepository;

    public DriverCommunication(IUserLocationRepository locationRepository,
                               IPassengerRequestRepository passengerRequestRepository) {
        super(locationRepository);

        this.passengerRequestRepository = passengerRequestRepository;

        setLocationsObservable(locationRepository.getPassengers());
    }

    @Override
    public Observable<List<Passenger>> getPassengersObservable() {
        return getUsersObservable();
    }

    @Override
    public Observable<PassengerRequest> getPassengerRequestObservable(String driverId) {
        return passengerRequestRepository.getRequestObservable(driverId);
    }

    @Override
    public Completable postPassengerResponse(DriverResponse driverResponse) {
        return passengerRequestRepository.postResponse(driverResponse);
    }

    @Override
    protected Passenger createUser() {
        return new Passenger();
    }

    @Override
    public Completable postLocation(Driver driver) {
        return locationRepository.postDriverLocation(driver.getId(), driver.getLocation());
    }
}
