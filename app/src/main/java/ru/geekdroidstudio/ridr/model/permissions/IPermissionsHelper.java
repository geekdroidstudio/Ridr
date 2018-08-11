package ru.geekdroidstudio.ridr.model.permissions;

import android.app.Activity;
import android.content.Context;

public interface IPermissionsHelper {
    boolean checkLocationPermission(Context context);

    String[] getLocationPermiss();

    int getLocationPermissReqCode();

    boolean isPermissReqResultGranted(int requestCode, String[] permissions, int[] grantResults);

    void requestLocationPermissions(Activity activity);
}
