package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapView;

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

}
