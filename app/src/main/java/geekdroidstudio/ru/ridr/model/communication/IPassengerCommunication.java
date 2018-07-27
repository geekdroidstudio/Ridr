package geekdroidstudio.ru.ridr.model.communication;


import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IPassengerCommunication {
    /*
     * Обновляет данные о местонахождении пассажира
     */
    Completable postLocation(Passenger passenger);

    /*
     * Для подписки на обновление местоположений водителей
     */
    Observable<List<Driver>> getDriversObservable();

    /*
     * Отправляет запрос водителю на добавление своего маршрута
     */
    Completable postPassengerRouteForDriver(PassengerRequest passengerRequest);

    /*
     * Для подписки на ответ водителя к зопросу пассажира
     */
    Observable<DriverResponse> getDriverResponseObservable();
}
