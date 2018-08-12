package ru.geekdroidstudio.ridr.model.communication.request;


import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.geekdroidstudio.ridr.model.communication.request.entity.DriverResponse;
import ru.geekdroidstudio.ridr.model.communication.request.entity.PassengerRequest;

public interface IPassengerRequestRepository {

    Single<DriverResponse> postRequest(PassengerRequest request);

    Observable<PassengerRequest> getRequestObservable(String id);

    Completable postResponse(DriverResponse response);
}
