package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.entity.Driver;
import geekdroidstudio.ru.ridr.model.communication.entity.Passenger;
import geekdroidstudio.ru.ridr.model.communication.location.IUserLocationRepository;
import geekdroidstudio.ru.ridr.model.communication.request.IPassengerRequestRepository;
import geekdroidstudio.ru.ridr.model.communication.request.entity.DriverResponse;
import geekdroidstudio.ru.ridr.model.communication.request.entity.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class PassengerCommunication extends UserCommunication<Driver>
        implements IPassengerCommunication {

    private IPassengerRequestRepository passengerRequestRepository;

    public PassengerCommunication(IUserLocationRepository locationRepository,
                                  IPassengerRequestRepository passengerRequestRepository) {
        super(locationRepository);

        this.passengerRequestRepository = passengerRequestRepository;

        setLocationsObservable(locationRepository.getDrivers());
    }

    @Override
    public Observable<List<Driver>> getDriversObservable() {
        return getUsersObservable();
    }

    @Override
    public Single<DriverResponse> postPassengerRequest(PassengerRequest passengerRequest) {
        return passengerRequestRepository.postRequest(passengerRequest);
    }

    @Override
    protected Driver createUser() {
        return new Driver();
    }


    @Override
    public Completable postLocation(Passenger passenger) {
        return locationRepository.postPassengerLocation(passenger.getId(), passenger.getLocation());
    }
}
