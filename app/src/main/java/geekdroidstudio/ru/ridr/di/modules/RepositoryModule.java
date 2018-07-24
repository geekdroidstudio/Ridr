package geekdroidstudio.ru.ridr.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.Repository;
import geekdroidstudio.ru.ridr.model.api.IApiService;


@Singleton
@Module(includes = ApiModule.class)
public class RepositoryModule {

    @Provides
    public Repository repository(IApiService apiService) {
        return new Repository(apiService);
    }
}
