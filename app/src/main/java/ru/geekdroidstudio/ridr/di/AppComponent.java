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
import ru.geekdroidstudio.ridr.ui.activities.user.authentication.AuthenticationActivity;
import ru.geekdroidstudio.ridr.ui.fragments.map.MapFragment;
import ru.geekdroidstudio.ridr.ui.fragments.routedata.RouteDataPresenter;
import ru.geekdroidstudio.ridr.ui.fragments.signin.SignInFragment;
import ru.geekdroidstudio.ridr.ui.fragments.signup.SignUpFragment;


@Singleton
@Component(modules = {RepositoryModule.class, CommunicationModule.class,
        AuthenticationModule.class, MapHelperModule.class, PermissionModule.class, AppModule.class})
public interface AppComponent {
    void inject(RouteDataPresenter routeDataFragmentPresenter);

    void inject(MapFragment mapFragment);

    void inject(SignInFragment signInFragment);

    void inject(SignUpFragment signUpFragment);

    void inject(PassengerPresenter presenter);

    void inject(DriverPresenter presenter);

    void inject(AuthenticationActivity authenticationActivity);

    void inject(PassengerActivity passengerActivity);

    void inject(DriverActivity driverActivity);
}
