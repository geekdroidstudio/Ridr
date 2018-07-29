package geekdroidstudio.ru.ridr.view.fragments.routeDataFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.RouteDataPresenter;
import geekdroidstudio.ru.ridr.view.adapters.AutocompleteAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class RouteDataFragment extends MvpAppCompatFragment implements RouteDataView {
    public static final String TAG = RouteDataFragment.class.getSimpleName();
    private static final LatLngBounds BOUNDS_RUSSIA = new LatLngBounds.Builder()
            .include(new LatLng(55.751244, 37.618423)).build();


    @BindView(R.id.lly_fr_route_data_root_view)
    LinearLayout rootView;
    @BindView(R.id.atv_fr_route_data_start_point)
    AutoCompleteTextView startPointAutoText;
    @BindView(R.id.atv_fr_route_data_end_point)
    AutoCompleteTextView endPointAutoText;

    @InjectPresenter
    RouteDataPresenter routeDataPresenter;

    private AutocompleteAdapter startPointAdapter;
    private AutocompleteAdapter endPointAdapter;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private Unbinder unbinder;

    public RouteDataFragment() {

    }

    public static RouteDataFragment newInstance() {
        return new RouteDataFragment();
    }

    @ProvidePresenter
    public RouteDataPresenter provideRoutePresenter() {
        RouteDataPresenter presenter =
                new RouteDataPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
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
        View view = inflater.inflate(R.layout.fragment_route_data, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void init() {
        Context context = getContext();
        assert context != null;

        GeoDataClient mGeoDataClient = Places.getGeoDataClient(context);

        startPointAdapter = new AutocompleteAdapter(context, mGeoDataClient, BOUNDS_RUSSIA, null);
        startPointAutoText.setAdapter(startPointAdapter);
        startPointAutoText.setThreshold(3);
        startPointAutoText.setOnItemClickListener(createPlaceClickListener(startPointAutoText.getId()));

        endPointAdapter = new AutocompleteAdapter(context, mGeoDataClient, BOUNDS_RUSSIA, null);
        endPointAutoText.setAdapter(endPointAdapter);
        endPointAutoText.setThreshold(3);
        endPointAutoText.setOnItemClickListener(createPlaceClickListener(endPointAutoText.getId()));
    }

    @Override
    public void routeLoadCompleted(List<LatLng> routePoints) {
        onFragmentInteractionListener.routeCreated(routePoints);
    }

    @Override
    public void showErrorLoadRoute() {
        Snackbar snackbar = Snackbar.make(rootView,
                R.string.error_load_route, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.retry), view -> routeDataPresenter.retryLoadRoute());
        snackbar.show();
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

    @NonNull
    private AdapterView.OnItemClickListener createPlaceClickListener(final int viewId) {
        return (parent, view, position, id) -> {
            onFragmentInteractionListener.hideKeyboard(view.getApplicationWindowToken());

            switch (viewId) {
                case R.id.atv_fr_route_data_start_point: {
                    AutocompletePrediction item = startPointAdapter.getItem(position);
                    routeDataPresenter.onStartPointSelected(item);
                    break;
                }
                case R.id.atv_fr_route_data_end_point: {
                    AutocompletePrediction item = endPointAdapter.getItem(position);
                    routeDataPresenter.onEndPointSelected(item);
                    break;
                }
                /*в этом кейсе мы можем обрабатывать промежуточные точки маршрута
                 *
                 *   default:{
                 *      doSomething()
                 *      break;
                 *   }
                 */
            }
        };
    }

    public interface OnFragmentInteractionListener {
        void routeCreated(List<LatLng> routePoints);

        void hideKeyboard(IBinder windowToken);
    }
}
