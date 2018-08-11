package geekdroidstudio.ru.ridr.view.fragments.routeDataFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RouteDataView extends MvpView {

    void init();

    void routeLoadCompleted(DualTextRoute dualTextRoute, List<LatLng> latLngsList);

    void showErrorLoadRoute();
}
