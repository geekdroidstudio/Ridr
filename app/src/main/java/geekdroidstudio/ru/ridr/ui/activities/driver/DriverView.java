package geekdroidstudio.ru.ridr.ui.activities.driver;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;
import geekdroidstudio.ru.ridr.ui.activities.userbase.UserBaseView;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface DriverView extends UserBaseView {

    void showRouteOnMap(List<LatLng> routePoints);

    void showDriverOnMap(User user);

    void showPassengersOnMap(List<? extends User> users);

    void showPassengersOnList(List<UserAndRoute<? extends User>> passengersAndRoutes);

    void showPassengerRequest(UserAndRoute<Passenger> passengerAndRoute);
}
