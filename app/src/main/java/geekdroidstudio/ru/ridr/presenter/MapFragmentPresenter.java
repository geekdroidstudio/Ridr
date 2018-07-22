package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import geekdroidstudio.ru.ridr.view.fragments.MapView;

@InjectViewState
public class MapFragmentPresenter extends MvpPresenter<MapView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showLoading();
        getViewState().loadMap();
    }

    public void onMapReady() {
        getViewState().hideLoading();
        getViewState().showDummyData();
    }

    public void onMapClick(LatLng latLng) {
    }

    public void onMarkerClick(Marker marker) {

    }
}
