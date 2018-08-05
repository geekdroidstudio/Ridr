package geekdroidstudio.ru.ridr.di.modules;

import android.content.Context;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.location.ILocationProvider;
import geekdroidstudio.ru.ridr.model.location.google.GoogleLocation;

@Singleton
@Module(includes = AppModule.class)
public class LocationModule {

    @Provides
    public ILocationProvider googleProvider(LocationManager locationManager,
                                            FusedLocationProviderClient fusedLocationProviderClient,
                                            SettingsClient settingsClient) {
        return new GoogleLocation(locationManager, fusedLocationProviderClient, settingsClient);
    }

    @Provides
    public LocationManager locationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    public FusedLocationProviderClient fusedLocationProviderClient(Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @Provides
    public SettingsClient settingsClient(Context context) {
        return LocationServices.getSettingsClient(context);
    }
}
