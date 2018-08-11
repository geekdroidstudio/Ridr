package ru.geekdroidstudio.ridr;

import android.app.Application;

import ru.geekdroidstudio.ridr.di.AppComponent;
import ru.geekdroidstudio.ridr.di.DaggerAppComponent;
import ru.geekdroidstudio.ridr.di.modules.AppModule;
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

    public static App getInstance() {
        return instance;
    }

    public AppComponent getComponent() {
        return component;
    }
}
