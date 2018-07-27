package geekdroidstudio.ru.ridr.di.modules;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.mapHelper.IMapHelper;
import geekdroidstudio.ru.ridr.model.mapHelper.googleMapHelper.GoogleMapHelper;

@Module
public class MapHelperModule {

    @Provides
    IMapHelper<GoogleMap, BitmapDescriptor, LatLng> googleMapHelper() {
        return new GoogleMapHelper();
    }
}
