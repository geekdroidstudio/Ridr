package geekdroidstudio.ru.ridr.model.api;

import geekdroidstudio.ru.ridr.model.repository.entity.RouteDrivingModelResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiService {
    @GET("directions/json")
    Single<RouteDrivingModelResponse> getRoute(@Query(value = "mode") String mode,
                                               @Query(value = "origin") String position,
                                               @Query(value = "destination") String destination,
                                               @Query(value = "language") String language,
                                               @Query(value = "key") String key);
}
