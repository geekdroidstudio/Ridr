package geekdroidstudio.ru.ridr.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.model.api.IApiService;
import geekdroidstudio.ru.ridr.model.location.ILocationProvider;


@Singleton
@Module(includes = {ApiModule.class, LocationModule.class})
public class RepositoryModule {

    @Provides
    public Repository repository(IApiService apiService, ILocationProvider locationProvider) {
        return new Repository(apiService, locationProvider);
    }
}
