package geekdroidstudio.ru.ridr.model.location.google;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;

import geekdroidstudio.ru.ridr.model.location.ILocationProvider;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;


public class GoogleLocation implements ILocationProvider {
    private static final long UPDATE_INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 5000;
    private static final String ERROR_LOCATIONS_SETTINGS = "Check location setting";
    private static final String ERROR_NOT_AVAILABILITY_LOCATION = "Not availability location service";

    private final SettingsClient settingsClient;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationManager locationManager;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private int REQUEST_CHECK_SETTINGS = 15;

    public GoogleLocation(LocationManager locationManager,
                          FusedLocationProviderClient fusedLocationProviderClient,
                          SettingsClient settingsClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        this.locationManager = locationManager;
        this.settingsClient = settingsClient;
    }

/*    @SuppressLint("MissingPermission")
    public Completable checkLocationResponse() {
        return Completable.create(emitter -> {
            LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                    .addLocationRequest(getLocationRequest())
                    .build();

            settingsClient
                    .checkLocationSettings(locationSettingsRequest)
                    .addOnSuccessListener(response -> emitter.onComplete())
                    .addOnFailureListener(exception -> {
                        switch (((ApiException) exception).getStatusCode()) {
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE: {
                                emitter.onError(new RuntimeException(ERROR_LOCATIONS_SETTINGS));
                                break;
                            }
                            default: {
                                emitter.onError(new RuntimeException());
                            }
                        }
                    });
        });
    }*/

    public Completable checkLocationResponse() {
        return Completable.create(emitter -> {
            LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                    .addLocationRequest(getLocationRequest())
                    .build();

            settingsClient
                    .checkLocationSettings(locationSettingsRequest)
                    .addOnCompleteListener(task -> {
                        try {
                            LocationSettingsResponse response = task.getResult(ApiException.class);
                            emitter.onComplete();
                        } catch (ApiException exception) {
                            emitter.onError(exception);
                        }
                    });
        });
    }


    @SuppressLint("MissingPermission")
    public Observable<Location> listenLocation() {
        return Observable.create((ObservableEmitter<Location> emitter) -> {
            fusedLocationProviderClient
                    .getLastLocation()
                    .addOnSuccessListener(lastLocation -> {
                        if (lastLocation != null) {
                            emitter.onNext(lastLocation);
                        }
                    });

            locationRequest = getLocationRequest();
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    emitter.onNext(locationResult.getLastLocation());
                }

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                    if (!locationAvailability.isLocationAvailable()) {
                        emitter.onError(new RuntimeException(ERROR_NOT_AVAILABILITY_LOCATION));
                    }
                }
            };
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }).doOnTerminate(() -> { //нормально ли так делать с другого потока - doOnTerminate() -принимает Action
            if (locationCallback != null) {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                locationCallback = null;
            }
        });
    }

    private String getProviderType() {
        boolean isGPSLocation = false;
        if (locationManager != null) {
            isGPSLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return (isGPSLocation)
                ? LocationManager.GPS_PROVIDER
                : LocationManager.NETWORK_PROVIDER;
    }

    private LocationRequest getLocationRequest() {
        String providerType = getProviderType();
        return new LocationRequest()
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                /*setPriority((providerType.equalsIgnoreCase(LocationManager.GPS_PROVIDER))
                        ? LocationRequest.PRIORITY_HIGH_ACCURACY
                        : LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);*/
    }
}
