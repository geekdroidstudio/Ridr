package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.List;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataView;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class RouteDataFragmentPresenter extends MvpPresenter<RouteDataView> {
    @Inject
    Repository repository;

    private Scheduler scheduler;
    private String routeStartPoint;
    private String routeEndPoint;

    public RouteDataFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void onStartPointSelected(AutocompletePrediction item) {
        routeStartPoint = item.getPlaceId();
        checkNeedReloadRoute();
    }

    public void onEndPointSelected(AutocompletePrediction item) {
        routeEndPoint = item.getPlaceId();
        checkNeedReloadRoute();
    }

    private void checkNeedReloadRoute() {
        if (routeStartPoint != null && !routeStartPoint.isEmpty() &&
                routeEndPoint != null && !routeEndPoint.isEmpty()) {
            loadRouteByTwoPointPlaceId(routeStartPoint, routeEndPoint);
        }
    }

    @SuppressLint("CheckResult")
    private void loadRouteByTwoPointPlaceId(String startPointPlaceId, String endPointPlaceId) {
        repository.getRoute(startPointPlaceId, endPointPlaceId)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(response -> {
                    if (response.getStatus().equals("OK")) {
                        /*получаем пока 1 маршрут, может быть не один*/
                        String overViewPolyLine = response.getRoutes().get(0).getOverviewPolyline()
                                .getPoints();

                        /*Выполнив запрос и получив объект Route мы можем получить из
                         * него строку overViewPolyLine.В своем исходном состоянии она нам мало
                         * что дает.Для того, чтобы добыть из нее какую -то информацию,
                         * нам нужно расшифровать ее.Здесь нам придет на помощь класс PolyUtil
                         * из библиотеки Google Maps Android API utility library.
                         * PolyUtil содержит метод decode (), принимающий строку overViewPolyLine
                         * и возвращающий набор объектов LatLng, узлов нашего маршрута*/
                        List<LatLng> latLngsList = PolyUtil.decode(overViewPolyLine);

                        getViewState().routeLoadCompleted(latLngsList);
                    } else {
                        getViewState().showErrorLoadRoute();
                    }
                }, throwable -> getViewState().showErrorLoadRoute());

    }

    public void retryLoadRoute() {
        loadRouteByTwoPointPlaceId(routeStartPoint, routeEndPoint);
    }
}
