package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.Driver;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassengerMainView extends MvpView {

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    void showRouteOnMap(List<LatLng> routePoints);

    void showPassengerOnMap(User user);

    void showDriversOnMap(List<? extends User> users);

    void addDriver(UserAndRoute<Driver> userAndRoute);
}
