package geekdroidstudio.ru.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import geekdroidstudio.ru.ridr.di.modules.AuthenticationModule;

@Component(modules = {AuthenticationModule.class})
@Singleton
public interface AppComponent {

}
