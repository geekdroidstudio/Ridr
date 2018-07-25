package geekdroidstudio.ru.ridr.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapView;

@InjectViewState
public class MapFragmentPresenter extends MvpPresenter<MapView> {
    private static final int MAP_WITH_ROUTE_PADDING = 25;
    private GoogleMap googleMap;
    private Marker startMarker;
    private Marker endMarker;

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showLoading();
        getViewState().loadMap();
    }

    public void onMapReady(GoogleMap googleMap) {
        if (this.googleMap != null) {
            googleMap.setOnMapClickListener(null);
        }
        this.googleMap = googleMap;
        setupMap();

        getViewState().hideLoading();
    }

    private void setupMap() {
        googleMap.setOnMapClickListener(createOnMapClickListener());
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }

    private GoogleMap.OnMapClickListener createOnMapClickListener() {
        return latLng -> {/*do something action*/};
    }

    public void drawRoute(int size, int routeLineColor, float routeLineWidth,
                          List<LatLng> routePoints) {
        googleMap.clear();

        PolylineOptions line = new PolylineOptions();
        line.width(routeLineWidth).color(routeLineColor);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < routePoints.size(); i++) {
            if (i == 0) {
                MarkerOptions startMarkerOptions = new MarkerOptions().position(routePoints.get(i));
                getViewState().createStartMarkerOptions(startMarkerOptions);

            } else if (i == routePoints.size() - 1) {
                MarkerOptions endMarkerOptions = new MarkerOptions().position(routePoints.get(i));
                getViewState().createEndMarkerOptions(endMarkerOptions);
            }

            line.add(routePoints.get(i));
            latLngBuilder.include(routePoints.get(i));
        }
        googleMap.addPolyline(line);

        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size,
                MAP_WITH_ROUTE_PADDING);
        googleMap.moveCamera(track);

    }

    public void showStartMarker(MarkerOptions icon) {
        startMarker = googleMap.addMarker(icon);
    }

    public void showEndMarker(MarkerOptions icon) {
        endMarker = googleMap.addMarker(icon);
    }

   /* private void clearMarkers() {
        googleMap.clear();
        *//*if (startMarker != null) {
            startMarker.remove();
        }
        if (endMarker != null) {
            endMarker.remove();
        }*//*
    }*/
}
