package geekdroidstudio.ru.ridr;

import android.app.Application;

import geekdroidstudio.ru.ridr.di.AppComponent;
import geekdroidstudio.ru.ridr.di.DaggerAppComponent;
import geekdroidstudio.ru.ridr.di.modules.AppModule;
import timber.log.Timber;

public class App extends Application {
    private static App instance;

	private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance()
    {
        return instance;
    }

    public AppComponent getComponent() {
        return component;
    }
}
