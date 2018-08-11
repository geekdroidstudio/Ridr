package geekdroidstudio.ru.ridr.model.communication.request;


import geekdroidstudio.ru.ridr.model.communication.request.entity.DriverResponse;
import geekdroidstudio.ru.ridr.model.communication.request.entity.PassengerRequest;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IPassengerRequestRepository {

    Single<DriverResponse> postRequest(PassengerRequest request);

    Observable<PassengerRequest> getRequestObservable(String id);

    Completable postResponse(DriverResponse response);
}
