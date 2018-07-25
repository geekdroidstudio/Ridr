package geekdroidstudio.ru.ridr;

import android.app.Application;

import geekdroidstudio.ru.ridr.di.AppComponent;
import geekdroidstudio.ru.ridr.di.DaggerAppComponent;

public class App extends Application {
    private static App instance;

    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
