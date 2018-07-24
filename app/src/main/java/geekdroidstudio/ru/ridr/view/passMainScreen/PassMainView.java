package geekdroidstudio.ru.ridr.view.passMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassMainView extends MvpView {

    void showMapFragment();

    void showRouteDataFragment();

    void showRouteInMapFragment(List<LatLng> routePoints);
}
