package ru.geekdroidstudio.ridr.model.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekdroidstudio.ridr.model.repository.entity.RouteDrivingModelResponse;

public interface IApiService {
    @GET("directions/json")
    Single<RouteDrivingModelResponse> getRoute(@Query(value = "mode") String mode,
                                               @Query(value = "origin") String position,
                                               @Query(value = "destination") String destination,
                                               @Query(value = "language") String language,
                                               @Query(value = "key") String key);
}
