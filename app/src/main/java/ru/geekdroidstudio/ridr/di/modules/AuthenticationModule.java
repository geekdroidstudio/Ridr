package ru.geekdroidstudio.ridr.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekdroidstudio.ridr.model.authentication.AuthDatabase;
import ru.geekdroidstudio.ridr.model.authentication.Authentication;

@Module(includes = {AuthDatabaseModule.class})
public class AuthenticationModule {

    @Provides
    Authentication provideAuthentication(AuthDatabase authDatabase) {
        return new Authentication(authDatabase);
    }
}
