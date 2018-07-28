package geekdroidstudio.ru.ridr;

import android.app.Application;

import geekdroidstudio.ru.ridr.di.AppComponent;
import geekdroidstudio.ru.ridr.di.DaggerAppComponent;
import timber.log.Timber;

public class App extends Application {

	private static AppComponent component;

	@Override
	public void onCreate() {
		super.onCreate();
		Timber.plant(new Timber.DebugTree());
		component = DaggerAppComponent.create();
	}

	public static AppComponent getComponent(){
		return component;
	}
}
