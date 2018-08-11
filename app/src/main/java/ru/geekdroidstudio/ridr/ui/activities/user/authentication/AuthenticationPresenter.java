package ru.geekdroidstudio.ridr.ui.activities.user.authentication;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class AuthenticationPresenter extends MvpPresenter<AuthenticationView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().checkPermissions();
    }

    public void locationPermissionsGranted() {
        getViewState().showAuthorizationFragment();
    }

    public void locationPermissionsNotGranted() {
        getViewState().requestPermissions();
    }

    public void onLocationPermissionsResult(boolean permissLocationGranted) {
        if (permissLocationGranted) {
            locationPermissionsGranted();
            return;
        }
        getViewState().showErrorPermissionsMsg();
    }
}