package geekdroidstudio.ru.ridr.ui.fragments.map;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.routes.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.User;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showLoading();
        getViewState().initMap();
    }

    public void onMapReady(GoogleMap googleMap) {
        getViewState().hideLoading();
        getViewState().showMap(googleMap);
    }

    public void showRoute(List<LatLng> routePoints) {
        getViewState().drawRoute(routePoints);
    }

    public void showUser(User user) {
        getViewState().drawUser(mapCoordsToLatLang(user.getLocation()));
    }

    public void showMapObjects(List<? extends User> mapObjects) {
        List<LatLng> latLngList = new ArrayList<>();
        for (User mapObject : mapObjects) {
            latLngList.add(mapCoordsToLatLang(mapObject.getLocation()));
        }
        getViewState().drawMapObjects(latLngList);
    }

    private LatLng mapCoordsToLatLang(Coordinate coordinate) {
        return new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
    }
}
