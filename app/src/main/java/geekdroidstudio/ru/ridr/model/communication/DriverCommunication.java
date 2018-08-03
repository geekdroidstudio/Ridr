package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.repository.IUserLocationRepository;
import geekdroidstudio.ru.ridr.model.communication.repository.request.IPassengerRequestRepository;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import io.reactivex.Completable;
import io.reactivex.Observable;

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
