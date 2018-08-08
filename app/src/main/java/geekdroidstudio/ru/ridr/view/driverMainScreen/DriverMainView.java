package geekdroidstudio.ru.ridr.view.driverMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.Passenger;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import geekdroidstudio.ru.ridr.model.entity.users.UserAndRoute;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface DriverMainView extends MvpView {

    void showRouteOnMap(List<LatLng> routePoints);

    void showDriverOnMap(User user);

    void showPassengersOnMap(List<? extends User> users);

    void resolveLocationException(ApiException exception);

    void showLocationSettingsError();

    void showPassengersOnList(List<UserAndRoute<? extends User>> passengersAndRoutes);

    void showPassengerRequest(UserAndRoute<Passenger> passengerAndRoute);
}
