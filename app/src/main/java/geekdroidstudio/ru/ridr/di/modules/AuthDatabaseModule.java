package geekdroidstudio.ru.ridr.di.modules;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.authentication.AuthDatabase;

@Module
public class AuthDatabaseModule {

    @Provides
    AuthDatabase provideAuthDatabase() {
        return new AuthDatabase();
    }
}
