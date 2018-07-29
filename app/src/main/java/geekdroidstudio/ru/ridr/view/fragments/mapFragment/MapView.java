package geekdroidstudio.ru.ridr.view.fragments.mapFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MapView extends MvpView {
    void initMap();

    void showRoute(List<LatLng> routePoints);

    void showLoading();

    void hideLoading();

    void showMap(GoogleMap map);

    void drawRoute(List<LatLng> routePoints);
}
