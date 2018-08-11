package ru.geekdroidstudio.ridr.model.communication.location;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.communication.location.entity.Coordinate;

public interface IUserLocationRepository {

    Single<User> getUser(String id);

    Completable postDriverLocation(String id, Coordinate location);

    Completable postPassengerLocation(String id, Coordinate location);

    Observable<Map<String, Coordinate>> getDrivers();

    Observable<Map<String, Coordinate>> getPassengers();
}
