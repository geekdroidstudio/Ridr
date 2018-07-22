package geekdroidstudio.ru.ridr.view.fragments;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MapView extends MvpView{

    void showDummyData();

    void showLoading();

    void loadMap();

    void hideLoading();

    void setupMap();

    void showMarker(LatLng latLng);
}
