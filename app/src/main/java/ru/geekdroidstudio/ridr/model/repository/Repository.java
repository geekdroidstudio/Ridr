package ru.geekdroidstudio.ridr.model.repository;

import android.location.Location;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ru.geekdroidstudio.ridr.BuildConfig;
import ru.geekdroidstudio.ridr.model.api.IApiService;
import ru.geekdroidstudio.ridr.model.location.ILocationProvider;
import ru.geekdroidstudio.ridr.model.repository.entity.RouteDrivingModelResponse;

public class Repository {
    private static final String API_KEY = BuildConfig.DIRECTIONS_API_KEY;
    private static final String LANGUAGE = "ru";
    private static final String MODE = "driving";
    private static final String PLACE_PARAM_PRECEDING = "place_id:";

    private final IApiService apiService;
    private final ILocationProvider locationProvider;

    public Repository(IApiService apiService, ILocationProvider locationProvider) {
        this.apiService = apiService;
        this.locationProvider = locationProvider;
    }

    public Single<RouteDrivingModelResponse> getRoute(String startPlaceId, String endPlaceId) {
        return apiService.getRoute(MODE, PLACE_PARAM_PRECEDING + startPlaceId,
                PLACE_PARAM_PRECEDING + endPlaceId, LANGUAGE, API_KEY);
    }

    public Observable<Location> startListenLocation() {
        return locationProvider
                .listenLocation();
    }

    public Completable checkLocationResponse() {
        return locationProvider
                .checkLocationResponse();
    }
}
