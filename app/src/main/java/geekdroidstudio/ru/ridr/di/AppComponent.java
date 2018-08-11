package geekdroidstudio.ru.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import geekdroidstudio.ru.ridr.di.modules.AppModule;
import geekdroidstudio.ru.ridr.di.modules.AuthenticationModule;
import geekdroidstudio.ru.ridr.di.modules.CommunicationModule;
import geekdroidstudio.ru.ridr.di.modules.MapHelperModule;
import geekdroidstudio.ru.ridr.di.modules.PermissionModule;
import geekdroidstudio.ru.ridr.di.modules.RepositoryModule;
import geekdroidstudio.ru.ridr.ui.activities.driver.DriverActivity;
import geekdroidstudio.ru.ridr.ui.activities.driver.DriverPresenter;
import geekdroidstudio.ru.ridr.ui.activities.passenger.PassengerActivity;
import geekdroidstudio.ru.ridr.ui.activities.passenger.PassengerPresenter;
import geekdroidstudio.ru.ridr.ui.activities.signin.SignInActivity;
import geekdroidstudio.ru.ridr.ui.fragments.authentication.AuthenticationFragment;
import geekdroidstudio.ru.ridr.ui.fragments.map.MapFragment;
import geekdroidstudio.ru.ridr.ui.fragments.registration.RegistrationFragment;
import geekdroidstudio.ru.ridr.ui.fragments.routedata.RouteDataPresenter;


@Singleton
@Component(modules = {RepositoryModule.class, CommunicationModule.class,
        AuthenticationModule.class, MapHelperModule.class, PermissionModule.class, AppModule.class})
public interface AppComponent {
    void inject(RouteDataPresenter routeDataFragmentPresenter);

    void inject(MapFragment mapFragment);

    void inject(AuthenticationFragment authenticationFragment);

    void inject(RegistrationFragment registrationFragment);

    void inject(PassengerPresenter presenter);

    void inject(DriverPresenter presenter);

    void inject(SignInActivity signInActivity);

    void inject(PassengerActivity passengerActivity);

    void inject(DriverActivity driverActivity);
}
