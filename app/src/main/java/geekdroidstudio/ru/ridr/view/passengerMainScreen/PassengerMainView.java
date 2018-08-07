package geekdroidstudio.ru.ridr.view.passengerMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.User;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PassengerMainView extends MvpView {

    //LatLang - временное решение - вместо них, лучше использовать свои класс координат
    void showRouteInMapFragment(List<LatLng> routePoints);

    void showPassengerOnMap(User user);

    void showDriversOnMap(List<? extends User> users);

    void showLocationSettingsError();

    void resolveLocationException(ApiException apiException);
}
