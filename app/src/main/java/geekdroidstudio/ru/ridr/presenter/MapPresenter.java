package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapView;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showLoading();
        getViewState().loadMap();
    }

    public void onMapReady() {
        getViewState().hideLoading();
        getViewState().setupMap();

    }

    public void onMapClick(LatLng latLng) {
        getViewState().showMarker(latLng);
    }

    public void onMarkerClick(Marker marker) {

    }
}
