package geekdroidstudio.ru.ridr.model.communication;


import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.entity.Driver;
import geekdroidstudio.ru.ridr.model.communication.entity.Passenger;
import geekdroidstudio.ru.ridr.model.communication.request.entity.DriverResponse;
import geekdroidstudio.ru.ridr.model.communication.request.entity.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

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
    Single<DriverResponse> postPassengerRequest(PassengerRequest passengerRequest);
}
