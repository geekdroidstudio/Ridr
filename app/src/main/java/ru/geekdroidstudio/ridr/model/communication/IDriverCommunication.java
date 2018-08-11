package ru.geekdroidstudio.ridr.model.communication;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.geekdroidstudio.ridr.model.communication.entity.Driver;
import ru.geekdroidstudio.ridr.model.communication.entity.Passenger;
import ru.geekdroidstudio.ridr.model.communication.request.entity.DriverResponse;
import ru.geekdroidstudio.ridr.model.communication.request.entity.PassengerRequest;

public interface IDriverCommunication {
    /*
     * Обновляет данные о местонахождении водителя
     */
    Completable postLocation(Driver driver);

    /*
     * Для подписки на обновление местоположений пассажиров
     */
    Observable<List<Passenger>> getPassengersObservable();

    /*
     * Для подписки на запросы пассажиров
     */
    Observable<PassengerRequest> getPassengerRequestObservable(String driverId);

    /*
     * Отправляет ответ на запрос пассажира
     */
    Completable postPassengerResponse(DriverResponse driverResponse);
}
