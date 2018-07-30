package geekdroidstudio.ru.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import geekdroidstudio.ru.ridr.di.modules.AuthenticationModule;
import geekdroidstudio.ru.ridr.di.modules.CommunicationModule;
import geekdroidstudio.ru.ridr.di.modules.MapHelperModule;
import geekdroidstudio.ru.ridr.di.modules.RepositoryModule;
import geekdroidstudio.ru.ridr.presenter.RouteDataPresenter;
import geekdroidstudio.ru.ridr.view.fragments.mapFragment.MapFragment;
import geekdroidstudio.ru.ridr.view.fragments.registrationFragment.RegistrationFragment;
import geekdroidstudio.ru.ridr.view.fragments.startAuthorisationFragment.StartAuthorisationFragment;


@Singleton
@Component(modules = {RepositoryModule.class, CommunicationModule.class, AuthenticationModule.class, MapHelperModule.class})
public interface AppComponent {

    void inject(RouteDataPresenter routeDataFragmentPresenter);
    void inject(MapFragment mapFragment);
    void inject(StartAuthorisationFragment startAuthorisationFragment);
    void inject(RegistrationFragment registrationFragment);
}
