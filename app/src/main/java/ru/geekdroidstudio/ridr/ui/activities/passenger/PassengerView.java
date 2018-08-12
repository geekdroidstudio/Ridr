package ru.geekdroidstudio.ridr.ui.activities.passenger;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.communication.entity.UserAndRoute;
import ru.geekdroidstudio.ridr.model.communication.request.entity.DriverResponse;
import ru.geekdroidstudio.ridr.ui.activities.userbase.UserBaseView;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassengerView extends UserBaseView {

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    void showRouteOnMap(List<LatLng> routePoints);

    void showPassengerOnMap(User user);

    void showDriversOnMap(List<? extends User> users);

    void showNeedRouteMessage();

    void showSendRequestDialog(UserAndRoute<? extends User> userAndRoute);

    void showResponse(DriverResponse driverResponse);

    void showDriversOnList(List<UserAndRoute<? extends User>> driversAndRoutes);
}
