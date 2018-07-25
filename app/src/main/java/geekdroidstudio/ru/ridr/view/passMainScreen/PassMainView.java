package geekdroidstudio.ru.ridr.view.passMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassMainView extends MvpView {

    void showMapFragment();


    void showRouteDataFragment();
}
