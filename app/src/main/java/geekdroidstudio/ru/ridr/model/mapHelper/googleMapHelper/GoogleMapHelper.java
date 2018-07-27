package geekdroidstudio.ru.ridr.model.mapHelper.googleMapHelper;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import geekdroidstudio.ru.ridr.model.mapHelper.IMapHelper;

public class GoogleMapHelper implements IMapHelper<GoogleMap, BitmapDescriptor, LatLng> {
    private static final int MAP_WITH_ROUTE_PADDING = 25;
    private GoogleMap googleMap;

    @Override
    public void init(GoogleMap map) {
        if (googleMap != null) {
            googleMap.setOnMapClickListener(null);
        }
        googleMap = map;
        setupMap();
    }

    @Override
    public void drawRoute(int size, int routeLineColor, float routeLineWidth, BitmapDescriptor endPointIcon, BitmapDescriptor startPointIcon, List<LatLng> routePoints) {
        if (googleMap == null) {
            return;
        }

        googleMap.clear();

        PolylineOptions line = new PolylineOptions();
        line.width(routeLineWidth).color(routeLineColor);

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        int routePointsSize = routePoints.size();

        for (int i = 0; i < routePointsSize; i++) {
            if (i == 0) {
                googleMap.addMarker(new MarkerOptions()
                        .position(routePoints.get(i))
                        .icon(startPointIcon));
            } else if (i == routePointsSize - 1) {
                googleMap.addMarker(new MarkerOptions()
                        .position(routePoints.get(routePointsSize - 1))
                        .icon(endPointIcon));
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

    private void setupMap() {
        googleMap.setOnMapClickListener(createOnMapClickListener());
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }

    private GoogleMap.OnMapClickListener createOnMapClickListener() {
        return latLng -> {/*do something action*/};
    }
}
