package ru.geekdroidstudio.ridr.ui.activities.user.authentication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import ru.geekdroidstudio.ridr.App;
import ru.geekdroidstudio.ridr.R;
import ru.geekdroidstudio.ridr.model.permissions.android.PermissionsHelper;
import ru.geekdroidstudio.ridr.ui.activities.driver.DriverActivity;
import ru.geekdroidstudio.ridr.ui.activities.passenger.PassengerActivity;
import ru.geekdroidstudio.ridr.ui.fragments.signin.SignInFragment;
import ru.geekdroidstudio.ridr.ui.fragments.signup.SignUpFragment;
import timber.log.Timber;

public class AuthenticationActivity extends MvpAppCompatActivity implements AuthenticationView,
        SignInFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener {

    public static final String USER_ID_KEY = "userIdKey";

    @InjectPresenter
    AuthenticationPresenter authenticationPresenter;

    @Inject
    PermissionsHelper permissionsHelper;

    boolean driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
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
            authenticationPresenter.onLocationPermissionsResult(permissionsHelper
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
    public void showAuthorizationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, SignInFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRegistrationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, SignUpFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void checkPermissions() {
        if (permissionsHelper.checkLocationPermission(this)) {
            authenticationPresenter.locationPermissionsGranted();
        } else {
            authenticationPresenter.locationPermissionsNotGranted();
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
                .setView(R.layout.dialog_signin_loading)//TODO: сделать для SDK 15 и выше
                .setCancelable(false)
                .create();

        alertDialog.show();
    }

    private void launchDriverActivity(String userId) {
        startActivity(new Intent(getApplicationContext(), DriverActivity.class)
                .putExtra(USER_ID_KEY, userId));
    }

    private void launchPassengerActivity(String userId) {
        startActivity(new Intent(getApplicationContext(), PassengerActivity.class)
                .putExtra(USER_ID_KEY, userId));
    }

    //StartAuthenticationFragment method implementations
    @Override
    public void changeFragmentToRegistration() {
        showRegistrationFragment();
    }

    //SignUpFragment method implementations
    @Override
    public void startDriverActivity(String userId) {
        launchDriverActivity(userId);
    }

    @Override
    public void startPassengerActivity(String userId) {
        launchPassengerActivity(userId);
    }
}
