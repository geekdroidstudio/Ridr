package ru.geekdroidstudio.ridr.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekdroidstudio.ridr.model.authentication.AuthDatabase;

@Module
public class AuthDatabaseModule {

    @Provides
    AuthDatabase provideAuthDatabase() {
        return new AuthDatabase();
    }
}
