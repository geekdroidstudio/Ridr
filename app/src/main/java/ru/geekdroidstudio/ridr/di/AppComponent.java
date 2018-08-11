package ru.geekdroidstudio.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekdroidstudio.ridr.di.modules.AppModule;
import ru.geekdroidstudio.ridr.di.modules.AuthenticationModule;
import ru.geekdroidstudio.ridr.di.modules.CommunicationModule;
import ru.geekdroidstudio.ridr.di.modules.MapHelperModule;
import ru.geekdroidstudio.ridr.di.modules.PermissionModule;
import ru.geekdroidstudio.ridr.di.modules.RepositoryModule;
import ru.geekdroidstudio.ridr.ui.activities.driver.DriverActivity;
import ru.geekdroidstudio.ridr.ui.activities.driver.DriverPresenter;
import ru.geekdroidstudio.ridr.ui.activities.passenger.PassengerActivity;
import ru.geekdroidstudio.ridr.ui.activities.passenger.PassengerPresenter;
import ru.geekdroidstudio.ridr.ui.activities.signin.SignInActivity;
import ru.geekdroidstudio.ridr.ui.fragments.authentication.AuthenticationFragment;
import ru.geekdroidstudio.ridr.ui.fragments.map.MapFragment;
import ru.geekdroidstudio.ridr.ui.fragments.registration.RegistrationFragment;
import ru.geekdroidstudio.ridr.ui.fragments.routedata.RouteDataPresenter;


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
