package geekdroidstudio.ru.ridr.model.users;


import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IUsersCommunication {

    /*
     * Обновляет данные о местонахождении водителя
     */
    Completable postDriverLocation(Driver driver);

    /*
     * Обновляет данные о местонахождении пассажира
     */
    Completable postPassengerLocation(Passenger passenger);

    /*
     * Для подписки на обновление местоположений водителей
     */
    Observable<List<Driver>> getDriversObservable();

    /*
     * Для подписки на обновление местоположений пассажиров
     */
    Observable<List<Passenger>> getPassengersObservable();

    /*
     * Отправляет запрос водителю на добавление своего маршрута
     */
    Completable postPassengerRouteForDriver(PassengerRequest passengerRequest);

    /*
     * Для подписки на запросы пассажиров
     */
    Observable<PassengerRequest> getPassengerRequestObservable();

    /*
     * Отправляет ответ на запрос пассажира
     */
    Completable postPassengerResponse(DriverResponse driverResponse);

    /*
     * Для подписки на ответ водителя к зопросу пассажира
     */
    Observable<DriverResponse> getDriverResponseObservable();
}
