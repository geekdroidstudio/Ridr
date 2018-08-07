package geekdroidstudio.ru.ridr.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.model.EmulateGeo;

@Singleton
@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public Context context() {
        return app;
    }

    @Provides
    public EmulateGeo getEmulateGeo() {
        return new EmulateGeo();
    }
}
