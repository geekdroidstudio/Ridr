package geekdroidstudio.ru.ridr.view.driverMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.model.entity.users.User;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface DriverMainView extends MvpView {
    void showMapFragment();

    void showRouteInMapFragment(List<LatLng> routePoints);

    void showUserInMapFragment(User user);

    void showMapObjectsInMapFragment(List<User> users);

    void showDriverRecycler();

    void resolveLocationException(ApiException exception);

    void showLocationSettingsError();
}
