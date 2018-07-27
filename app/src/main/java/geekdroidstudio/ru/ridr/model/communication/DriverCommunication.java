package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.repository.ICommunicationRepository;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class DriverCommunication extends UserCommunication<Driver, Passenger>
        implements IDriverCommunication {

    public DriverCommunication(ICommunicationRepository communicationRepository) {
        super(communicationRepository, communicationRepository.getPassengers());
    }

    @Override
    public Observable<List<Passenger>> getPassengersObservable() {
        return getUsersObservable();
    }

    @Override
    public Observable<PassengerRequest> getPassengerRequestObservable() {
        throw new RuntimeException("Этот метод ещё не реализован");
    }

    @Override
    public Completable postPassengerResponse(DriverResponse driverResponse) {
        throw new RuntimeException("Этот метод ещё не реализован");
    }

    @Override
    protected Passenger createUser() {
        return new Passenger();
    }
}
