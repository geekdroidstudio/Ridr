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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.MapPresenter;


public class MapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    @BindView(R.id.pb_fragment_map_loading)
    ProgressBar loadingProgress;

    @InjectPresenter
    MapPresenter mapPresenter;

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
                    + getString(R.string.error_implement_fragment_interaction_listener));
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
                   .alpha(Float.parseFloat(getString(R.string.map_fragment_map_marker_alpha))));
            //наверное лучше не в стрингах хранить
        } else {
            marker.setPosition(latLng);
        }
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

    public interface OnFragmentInteractionListener {

    }
}
