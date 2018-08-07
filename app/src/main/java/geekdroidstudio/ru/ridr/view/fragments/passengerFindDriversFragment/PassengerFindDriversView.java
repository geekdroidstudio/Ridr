package geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PassengerFindDriversView extends MvpView {
    void showMapFragment();
    //void viewDriverRoute(int id);
}