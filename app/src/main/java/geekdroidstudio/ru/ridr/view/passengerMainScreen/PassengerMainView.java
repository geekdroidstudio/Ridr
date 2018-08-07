package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassengerMainView extends MvpView {

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    void showRouteOnMap(List<LatLng> routePoints);

    void showPassengerOnMap(User user);

    void showDriversOnMap(List<? extends User> users);

    void showLocationSettingsError();

    void resolveLocationException(ApiException apiException);

    void showNeedRouteMessage();

    void showSendRequestDialog(UserAndRoute<? extends User> userAndRoute);

    void showResponse(DriverResponse driverResponse);

    void showDriversOnList(List<UserAndRoute<? extends User>> driversAndRoutes);
}
