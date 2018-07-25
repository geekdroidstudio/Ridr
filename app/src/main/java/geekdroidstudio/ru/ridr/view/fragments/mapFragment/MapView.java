package geekdroidstudio.ru.ridr.view.fragments.mapFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MapView extends MvpView{

    void showDummyData();

    void showLoading();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void loadMap();

    void hideLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setupMap();

    void showMarker(LatLng latLng);
}
