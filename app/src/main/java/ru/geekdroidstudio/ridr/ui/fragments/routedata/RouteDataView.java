package ru.geekdroidstudio.ridr.ui.fragments.routedata;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ru.geekdroidstudio.ridr.model.communication.location.entity.DualTextRoute;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RouteDataView extends MvpView {

    void init();

    void routeLoadCompleted(DualTextRoute dualTextRoute, List<LatLng> latLngsList);

    void showErrorLoadRoute();
}
