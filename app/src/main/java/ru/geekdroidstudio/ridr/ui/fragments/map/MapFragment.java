package ru.geekdroidstudio.ridr.ui.fragments.map;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.geekdroidstudio.ridr.App;
import ru.geekdroidstudio.ridr.R;
import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.maphelper.IMapHelper;


public class MapFragment extends MvpAppCompatFragment implements MapView {
    public static final String TAG = MapFragment.class.getSimpleName();
    @BindView(R.id.pb_fragment_map_loading)
    ProgressBar loadingProgress;
    @BindColor(R.color.colorPrimary)
    int routeLineColor;
    @BindDimen(R.dimen.routeLineWidth)
    float routeLineWidth;

    @InjectPresenter
    MapPresenter mapPresenter;

    @Inject
    IMapHelper<GoogleMap, BitmapDescriptor, LatLng> mapHelper;

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
        App.getInstance().getComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.gm_fragment_map_map);
        mapFragment.getMapAsync(createOnMapReadyCallback());
    }

    @Override
    public void showMap(GoogleMap map) {
        mapHelper.init(map);
    }

    @Override
    public void drawRoute(List<LatLng> routePoints) {
        mapHelper.drawRoute(getResources().getDisplayMetrics().widthPixels, routeLineColor,
                routeLineWidth, BitmapDescriptorFactory.fromResource(R.drawable.ic_start_route),
                BitmapDescriptorFactory.fromResource(R.drawable.ic_end_route), routePoints);
    }

    //надо как-то различать кого рисовать(т.е пассажира или водителя, пока настроено что объекты -
    // это водители
    @Override
    public void drawMapObjects(List<LatLng> mapObjects) {
        mapHelper.drawMapObjects(mapObjects, BitmapDescriptorFactory.fromResource(
                R.drawable.ic_passenger));
    }

    //надо как-то различать кого рисовать(т.е пассажира или водителя, пока настроено что объекты -
    // это водители
    @Override
    public void drawUser(LatLng user) {
        mapHelper.drawUser(user, BitmapDescriptorFactory.fromResource(R.drawable.ic_driver));
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    public void showRoute(List<LatLng> routePoints) {
        mapPresenter.showRoute(routePoints);
    }

    public void showUser(User user) {
        mapPresenter.showUser(user);
    }

    public void showUsersOnMap(List<? extends User> users) {
        mapPresenter.showMapObjects(users);
    }

    private OnMapReadyCallback createOnMapReadyCallback() {
        return googleMap -> mapPresenter.onMapReady(googleMap);
    }

    public interface OnFragmentInteractionListener {
    }
}
