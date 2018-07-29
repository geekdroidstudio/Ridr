package geekdroidstudio.ru.ridr.model.communication.repository.request;


import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IPassengerRequestRepository {

    Single<DriverResponse> postRequest(PassengerRequest request);

    Observable<PassengerRequest> getRequestObservable(String id);

    Completable postResponse(DriverResponse response);
}
