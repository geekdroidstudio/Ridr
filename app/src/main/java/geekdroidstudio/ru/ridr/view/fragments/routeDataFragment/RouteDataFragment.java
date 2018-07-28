package geekdroidstudio.ru.ridr.view.fragments.routeDataFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.RouteDataPresenter;
import geekdroidstudio.ru.ridr.view.adapters.AutocompleteAdapter;


public class RouteDataFragment extends MvpAppCompatFragment implements RouteDataView {
    private static final LatLngBounds BOUNDS_RUSSIA = initLatLangBounds();
    protected GeoDataClient mGeoDataClient;
    @BindView(R.id.atv_act_pass_main_start_point)
    AutoCompleteTextView startPointAutoText;
    @BindView(R.id.atv_act_pass_main_end_point)
    AutoCompleteTextView endPointAutoText;
    @InjectPresenter
    RouteDataPresenter routeDataPresenter;

    public RouteDataFragment() {

    }
    private AutocompleteAdapter startPointAdapter;
    private AutocompleteAdapter endPointAdapter;

    private static LatLngBounds initLatLangBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(55.751244, 37.618423));
        return builder.build();
    }

    private OnFragmentInteractionListener onFragmentInteractionListener;
    private Unbinder unbinder;

    @Override
    public void init() {
        mGeoDataClient = Places.getGeoDataClient(Objects.requireNonNull(getContext()));
        Context context = getContext();
        startPointAdapter = new AutocompleteAdapter(context, mGeoDataClient, BOUNDS_RUSSIA, null);

        startPointAutoText.setAdapter(startPointAdapter);
        startPointAutoText.setThreshold(3);
        //  startPointAutoText.addTextChangedListener(createPointTextWatcher(PointType.START_POINT));
        startPointAutoText.setOnItemClickListener(createPlaceClickListener(PointType.START_POINT));

        endPointAdapter = new AutocompleteAdapter(context, mGeoDataClient, BOUNDS_RUSSIA, null);

        endPointAutoText.setAdapter(endPointAdapter);
        endPointAutoText.setThreshold(3);
        //    endPointAutoText.addTextChangedListener(createPointTextWatcher(PointType.END_POINT));
        endPointAutoText.setOnItemClickListener(createPlaceClickListener(PointType.END_POINT));
    }

    public static RouteDataFragment newInstance() {
        return new RouteDataFragment();
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
        View view = inflater.inflate(R.layout.fragment_route_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    private TextWatcher createPointTextWatcher(PointType pointType) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (pointType) {
                    case START_POINT: {
                        routeDataPresenter.onStartPointTextChange(s.toString());
                        break;
                    }
                    case END_POINT: {
                        routeDataPresenter.onEndPointTextChange(s.toString());
                        break;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @NonNull
    private AdapterView.OnItemClickListener createPlaceClickListener(PointType pointType) {
        return (parent, view, position, id) -> {
            switch (pointType) {
                case START_POINT: {
                    AutocompletePrediction item = startPointAdapter.getItem(position);
                    routeDataPresenter.onStartPointSelected(item);
                    break;
                }
                case END_POINT: {
                    AutocompletePrediction item = endPointAdapter.getItem(position);
                    routeDataPresenter.onEndPointTextSelected(item);
                    break;
                }
            }
        };
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

    public enum PointType {
        START_POINT, END_POINT
    }


    public interface OnFragmentInteractionListener {

    }
}
