package geekdroidstudio.ru.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import geekdroidstudio.ru.ridr.di.modules.RepositoryModule;
import geekdroidstudio.ru.ridr.presenter.RouteDataFragmentPresenter;

@Singleton
@Component(modules = RepositoryModule.class)
public interface AppComponent {

    void inject(RouteDataFragmentPresenter routeDataFragmentPresenter);

}
