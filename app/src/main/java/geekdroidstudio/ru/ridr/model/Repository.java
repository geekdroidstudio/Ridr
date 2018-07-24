package geekdroidstudio.ru.ridr.model;

import geekdroidstudio.ru.ridr.model.api.IApiService;
import geekdroidstudio.ru.ridr.model.entity.RouteDrivingModelResponse;
import io.reactivex.Single;

public class Repository {
    private static final String API_KEY = "AIzaSyANcVP-QiS9rm53pmpJE3uPt98U0uWSH4c";
    private static final String LANGUAGE = "ru";
    private static final String MODE = "driving";

    private static final String PLACE_PARAM_PRECERDING = "place_id:";

    private IApiService apiService;

    public Repository(IApiService apiService) {
        this.apiService = apiService;
    }

    public Single<RouteDrivingModelResponse> getRoute(String startPlaceId, String endPlaceId) {
        return apiService.getRoute(MODE, PLACE_PARAM_PRECERDING + startPlaceId,
                PLACE_PARAM_PRECERDING + endPlaceId, LANGUAGE, API_KEY);
    }
}
