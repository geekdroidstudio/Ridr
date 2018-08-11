package ru.geekdroidstudio.ridr.model.communication;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.geekdroidstudio.ridr.model.communication.entity.Driver;
import ru.geekdroidstudio.ridr.model.communication.entity.Passenger;
import ru.geekdroidstudio.ridr.model.communication.request.entity.DriverResponse;
import ru.geekdroidstudio.ridr.model.communication.request.entity.PassengerRequest;

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
