package geekdroidstudio.ru.ridr.view.fragments.mapFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MapView extends MvpView {
    void loadMap();

    void drawRoute(List<LatLng> routePoints);

    void showLoading();

    void hideLoading();

    void createStartMarkerOptions(MarkerOptions startMarkerOptions);

    void createEndMarkerOptions(MarkerOptions endMarkerOptions);
}
