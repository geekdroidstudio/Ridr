package geekdroidstudio.ru.ridr.view.fragments.mapFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.MapFragmentPresenter;


public class MapFragment extends MvpAppCompatFragment implements MapView {
    public static final String TAG = MapFragment.class.getSimpleName();

    @BindView(R.id.pb_fragment_map_loading)
    ProgressBar loadingProgress;
    @BindColor(R.color.colorPrimary)
    int routeLineColor;
    @BindDimen(R.dimen.routeLineWidth)
    float routeLineWidth;

    @InjectPresenter
    MapFragmentPresenter mapPresenter;


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
        mapFragment.getMapAsync(createOnMapReadyCallback());
    }

    @Override
    public void drawRoute(List<LatLng> routePoints) {
        mapPresenter.drawRoute(getResources().getDisplayMetrics().widthPixels, routeLineColor,
                routeLineWidth, routePoints);
    }


    @Override
    public void showLoading() {
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgress.setVisibility(View.GONE);
    }

    @SuppressLint("CheckResult")
    @Override
    public void createStartMarkerOptions(MarkerOptions startMarkerOptions) {
        mapPresenter.showEndMarker(startMarkerOptions.icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_start_route)));
    }

    @Override
    public void createEndMarkerOptions(MarkerOptions endMarkerOptions) {
        mapPresenter.showStartMarker(endMarkerOptions
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_end_route)));
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

    private OnMapReadyCallback createOnMapReadyCallback() {
        return googleMap -> mapPresenter.onMapReady(googleMap);
    }


    public interface OnFragmentInteractionListener {
    }
}
