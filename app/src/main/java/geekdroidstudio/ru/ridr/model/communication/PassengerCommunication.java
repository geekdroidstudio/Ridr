package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.repository.ICommunicationRepository;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class PassengerCommunication extends UserCommunication<Passenger, Driver>
        implements IPassengerCommunication {

    public PassengerCommunication(ICommunicationRepository communicationRepository) {
        super(communicationRepository, communicationRepository.getPassengers());
    }

    @Override
    public Observable<List<Driver>> getDriversObservable() {
        return getUsersObservable();
    }

    @Override
    public Completable postPassengerRouteForDriver(PassengerRequest passengerRequest) {
        throw new RuntimeException("Этот метод ещё не реализован");
    }

    @Override
    public Observable<DriverResponse> getDriverResponseObservable() {
        throw new RuntimeException("Этот метод ещё не реализован");
    }

    @Override
    protected Driver createUser() {
        return new Driver();
    }
}
