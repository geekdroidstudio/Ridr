package geekdroidstudio.ru.ridr.model.communication.repository;

import java.util.Map;

import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IUserLocationRepository {

    Single<User> getUser(String id);

    Completable postDriverLocation(String id, Coordinate location);

    Completable postPassengerLocation(String id, Coordinate location);

    Observable<Map<String, Coordinate>> getDrivers();

    Observable<Map<String, Coordinate>> getPassengers();
}
