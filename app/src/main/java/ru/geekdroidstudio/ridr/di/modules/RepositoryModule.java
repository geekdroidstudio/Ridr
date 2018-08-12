package ru.geekdroidstudio.ridr.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekdroidstudio.ridr.model.api.IApiService;
import ru.geekdroidstudio.ridr.model.location.ILocationProvider;
import ru.geekdroidstudio.ridr.model.repository.Repository;


@Singleton
@Module(includes = {ApiModule.class, LocationModule.class})
public class RepositoryModule {

    @Provides
    public Repository repository(IApiService apiService, ILocationProvider locationProvider) {
        return new Repository(apiService, locationProvider);
    }
}
