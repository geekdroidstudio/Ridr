package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.repository.IUserLocationRepository;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class PassengerCommunication extends UserCommunication<Passenger, Driver>
        implements IPassengerCommunication {

    public PassengerCommunication(IUserLocationRepository communicationRepository) {
        super(communicationRepository, communicationRepository.getDrivers());
    }

    @Override
    public Observable<List<Driver>> getDriversObservable() {
        return getUsersObservable();
    }

    @Override
    public Observable<DriverResponse> postPassengerRouteForDriver(PassengerRequest passengerRequest) {
        throw new RuntimeException("Этот метод ещё не реализован");
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
