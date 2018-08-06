package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.User;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassengerMainView extends MvpView {

    void showFindDriversFragment();

    void showMapFragment();

    void showRouteDataFragment();

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    void showRouteInMapFragment(List<LatLng> routePoints);

    void showUserInMapFragment(User user);

    void showMapObjectsInMapFragment(List<User> users);

}
