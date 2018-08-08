package geekdroidstudio.ru.ridr.view.userMainScreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.permissions.android.PermissionsHelper;
import geekdroidstudio.ru.ridr.presenter.UserMainPresenter;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainActivity;
import geekdroidstudio.ru.ridr.view.fragments.registrationFragment.RegistrationFragment;
import geekdroidstudio.ru.ridr.view.fragments.startAuthenticationFragment.StartAuthenticationFragment;
import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainActivity;
import timber.log.Timber;

public class UserMainActivity extends MvpAppCompatActivity implements UserMainView,
        StartAuthenticationFragment.OnFragmentInteractionListener,
        RegistrationFragment.OnFragmentInteractionListener {

    public static final String USER_ID_KEY = "userIdKey";

    @InjectPresenter
    UserMainPresenter userMainPresenter;

    @Inject
    PermissionsHelper permissionsHelper;

    boolean driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        App.getInstance().getComponent().inject(this);
    }

    private AlertDialog alertDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hideLoading();
    }

    @Override
    public void requestPermissions() {
        permissionsHelper.requestLocationPermissions(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionsHelper.getLocationPermissReqCode()) {
            userMainPresenter.onLocationPermissionsResult(permissionsHelper
                    .isPermissReqResultGranted(requestCode, permissions, grantResults));
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showErrorPermissionsMsg() {
        Timber.d("PERMISSIONS NOT GRANTED");
    }

    //View method implementations
    @Override
    public void showStartAuthorisationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_user_main_frame, StartAuthenticationFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRegistrationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_user_main_frame, RegistrationFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void checkPermissions() {
        if (permissionsHelper.checkLocationPermission(this)) {
            userMainPresenter.locationPermissionsGranted();
        } else {
            userMainPresenter.locationPermissionsNotGranted();
        }
    }

    @Override
    public void onDriverSingingIn() {
        showLoading();
        driver = true;
    }

    @Override
    public void onPassengerSingingIn() {
        showLoading();
        driver = false;
    }

    @Override
    public void onSignedIn(String userId) {
        hideLoading();
        if (driver) {
            launchDriverActivity(userId);
        } else {
            launchPassengerActivity(userId);
        }
    }

    private void hideLoading() {
        if (alertDialog != null) {
            alertDialog.hide();
        }
    }


    private void showLoading() {
        alertDialog = new AlertDialog.Builder(this)
                .setView(R.layout.singning_in_loading)//TODO: сделать для SDK 15 и выше
                .setCancelable(false)
                .create();

        alertDialog.show();
    }

    private void launchDriverActivity(String userId) {
        startActivity(new Intent(getApplicationContext(), DriverMainActivity.class)
                .putExtra(USER_ID_KEY, userId));
    }

    private void launchPassengerActivity(String userId) {
        startActivity(new Intent(getApplicationContext(), PassengerMainActivity.class)
                .putExtra(USER_ID_KEY, userId));
    }

    //StartAuthenticationFragment method implementations
    @Override
    public void changeFragmentToRegistration() {
        showRegistrationFragment();
    }

    //RegistrationFragment method implementations
    @Override
    public void startDriverActivity(String userId) {
        launchDriverActivity(userId);
    }

    @Override
    public void startPassengerActivity(String userId) {
        launchPassengerActivity(userId);
    }
}
