package ru.geekdroidstudio.ridr.di.modules;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import dagger.Module;
import dagger.Provides;
import ru.geekdroidstudio.ridr.model.maphelper.IMapHelper;
import ru.geekdroidstudio.ridr.model.maphelper.googlemaphelper.GoogleMapHelper;

@Module
public class MapHelperModule {

    @Provides
    IMapHelper<GoogleMap, BitmapDescriptor, LatLng> googleMapHelper() {
        return new GoogleMapHelper();
    }
}
