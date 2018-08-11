package geekdroidstudio.ru.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import geekdroidstudio.ru.ridr.di.modules.AppModule;
import geekdroidstudio.ru.ridr.di.modules.AuthenticationModule;
import geekdroidstudio.ru.ridr.di.modules.CommunicationModule;
import geekdroidstudio.ru.ridr.di.modules.MapHelperModule;
import geekdroidstudio.ru.ridr.di.modules.PermissionModule;
import geekdroidstudio.ru.ridr.di.modules.RepositoryModule;
import geekdroidstudio.ru.ridr.presenter.DriverMainPresenter;
import geekdroidstudio.ru.ridr.presenter.PassengerMainPresenter;
import geekdroidstudio.ru.ridr.presenter.RouteDataPresenter;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainActivity;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.registrationFragment.RegistrationFragment;
import geekdroidstudio.ru.ridr.view.fragments.startAuthenticationFragment.StartAuthenticationFragment;
import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainActivity;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity;


@Singleton
@Component(modules = {RepositoryModule.class, CommunicationModule.class,
        AuthenticationModule.class, MapHelperModule.class, PermissionModule.class, AppModule.class})
public interface AppComponent {
    void inject(RouteDataPresenter routeDataFragmentPresenter);

    void inject(MapFragment mapFragment);

    void inject(StartAuthenticationFragment startAuthenticationFragment);

    void inject(RegistrationFragment registrationFragment);

    void inject(PassengerMainPresenter presenter);

    void inject(DriverMainPresenter presenter);

    void inject(UserMainActivity userMainActivity);

    void inject(PassengerMainActivity passengerMainActivity);

    void inject(DriverMainActivity driverMainActivity);
}
