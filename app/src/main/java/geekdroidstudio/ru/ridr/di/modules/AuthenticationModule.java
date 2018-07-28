package geekdroidstudio.ru.ridr.di.modules;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;

@Module
public class AuthenticationModule {

	@Provides
	Authentication provideAuthentication() {
		return new Authentication();
	}
}
