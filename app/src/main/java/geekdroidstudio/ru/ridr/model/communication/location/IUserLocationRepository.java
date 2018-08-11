package geekdroidstudio.ru.ridr.model.communication.location;

import java.util.Map;

import geekdroidstudio.ru.ridr.model.communication.entity.User;
import geekdroidstudio.ru.ridr.model.communication.location.entity.Coordinate;
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
