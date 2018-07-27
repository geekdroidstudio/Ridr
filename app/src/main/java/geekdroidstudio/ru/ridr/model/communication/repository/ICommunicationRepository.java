package geekdroidstudio.ru.ridr.model.communication.repository;

import java.util.Map;

import geekdroidstudio.ru.ridr.model.communication.entity.UserLocation;
import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ICommunicationRepository {

    Observable<User> getUser(String id);

    Completable postDriverLocation(UserLocation userLocation);

    Completable postPassengerLocation(UserLocation userLocation);

    Observable<Map<String, Coordinate>> getDrivers();

    Observable<Map<String, Coordinate>> getPassengers();
}
