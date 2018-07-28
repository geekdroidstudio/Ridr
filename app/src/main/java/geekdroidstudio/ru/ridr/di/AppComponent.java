package geekdroidstudio.ru.ridr.di;

import javax.inject.Singleton;

import dagger.Component;
import geekdroidstudio.ru.ridr.di.modules.AuthenticationModule;
import geekdroidstudio.ru.ridr.view.passMainScreen.MainActivity;

@Component(modules = {AuthenticationModule.class})
@Singleton
public interface AppComponent {
	void injects(MainActivity mainActivity);

}
