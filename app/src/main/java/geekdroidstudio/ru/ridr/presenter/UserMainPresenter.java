package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainView;

@InjectViewState
public class UserMainPresenter extends MvpPresenter<UserMainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().checkPermissions();
    }

    public void locationPermissionsGranted() {
        getViewState().showStartAuthorisationFragment();
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