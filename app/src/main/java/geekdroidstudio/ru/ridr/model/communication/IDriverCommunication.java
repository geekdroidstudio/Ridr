package geekdroidstudio.ru.ridr.model.communication;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
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
