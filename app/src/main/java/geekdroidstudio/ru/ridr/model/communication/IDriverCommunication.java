package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.communication.entity.Driver;
import geekdroidstudio.ru.ridr.model.communication.entity.Passenger;
import geekdroidstudio.ru.ridr.model.communication.request.entity.DriverResponse;
import geekdroidstudio.ru.ridr.model.communication.request.entity.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;

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
