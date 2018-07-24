package geekdroidstudio.ru.ridr.view.fragments.mapFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.directionApiResponse.Route;
import geekdroidstudio.ru.ridr.presenter.MapFragmentPresenter;


public class MapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    public static final String TAG = MapFragment.class.getSimpleName();

    @BindView(R.id.pb_fragment_map_loading)
    ProgressBar loadingProgress;

    @InjectPresenter
    MapFragmentPresenter mapPresenter;

    private GoogleMap map;
    private Marker marker;
    private Unbinder unbinder;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    public MapFragment() {

    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_frag_interact_list));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void loadMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.gm_fragment_map_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void setupMap() {
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);

        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }

    @Override
    public void showMarker(LatLng latLng) {
        if (marker == null) {
           marker=  map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .alpha(Float.parseFloat(getString(R.string.map_fragment_map_marker_alpha))));/*наверное лучше не в стрингах хранить*/
        } else {
            marker.setPosition(latLng);
        }
    }

    @Override
    public void showRoute(List<LatLng> routePoints) {
        PolylineOptions line = new PolylineOptions();
        line
                .width(4f)
                .color(R.color.colorAccent);

        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < routePoints.size(); i++) {
            if (i == 0) {
             /*   MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(routePoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_a));
                map.addMarker(startMarkerOptions);*/
            } else if (i == routePoints.size() - 1) {
                /*MarkerOptions endMarkerOptions = new MarkerOptions()
                        .position(routePoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_b));
                map.addMarker(endMarkerOptions);*/
            }

            line.add(routePoints.get(i));
            latLngBuilder.include(routePoints.get(i));
        }
        map.addPolyline(line);

        int size = getResources().getDisplayMetrics().widthPixels;
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
        map.moveCamera(track);
    }

    //onMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        mapPresenter.onMapReady();
    }

    //onMapClickCallback
    @Override
    public void onMapClick(LatLng latLng) {
        mapPresenter.onMapClick(latLng);
    }

    //onMarkerClickCallback
    @Override
    public boolean onMarkerClick(Marker marker) {
        mapPresenter.onMarkerClick(marker);
        return true;
    }

    @Override
    public void showDummyData() {
        LatLng sydney = new LatLng(-34, 151);
        marker = map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void showLoading() {
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener { }
}
