package geekdroidstudio.ru.ridr.model;

import geekdroidstudio.ru.ridr.BuildConfig;
import geekdroidstudio.ru.ridr.model.api.IApiService;
import geekdroidstudio.ru.ridr.model.entity.RouteDrivingModelResponse;
import io.reactivex.Single;

public class Repository {
    private static final String API_KEY = BuildConfig.DIRECTIONS_API_KEY;
    private static final String LANGUAGE = "ru";
    private static final String MODE = "driving";
    private static final String PLACE_PARAM_PRECEDING = "place_id:";

    private IApiService apiService;

    public Repository(IApiService apiService) {
        this.apiService = apiService;
    }

    public Single<RouteDrivingModelResponse> getRoute(String startPlaceId, String endPlaceId) {
        return apiService.getRoute(MODE, PLACE_PARAM_PRECEDING + startPlaceId,
                PLACE_PARAM_PRECEDING + endPlaceId, LANGUAGE, API_KEY);
    }
}
