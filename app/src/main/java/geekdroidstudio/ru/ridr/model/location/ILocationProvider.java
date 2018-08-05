package geekdroidstudio.ru.ridr.model.location;

import android.location.Location;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ILocationProvider {

    Completable checkLocationResponse();

    Observable<Location> listenLocation();
}
