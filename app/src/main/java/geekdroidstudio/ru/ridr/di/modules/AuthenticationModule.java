package geekdroidstudio.ru.ridr.di.modules;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.server.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;

@Module(includes = {AuthDatabaseModule.class})
public class AuthenticationModule {

	@Provides
	Authentication provideAuthentication(AuthDatabase authDatabase) {
		return new Authentication(authDatabase);
	}
}
