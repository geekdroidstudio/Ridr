package geekdroidstudio.ru.ridr.ui.activities.signin;

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
import geekdroidstudio.ru.ridr.ui.activities.driver.DriverActivity;
import geekdroidstudio.ru.ridr.ui.activities.passenger.PassengerActivity;
import geekdroidstudio.ru.ridr.ui.fragments.authentication.AuthenticationFragment;
import geekdroidstudio.ru.ridr.ui.fragments.registration.RegistrationFragment;
import timber.log.Timber;

public class SignInActivity extends MvpAppCompatActivity implements SignInView,
        AuthenticationFragment.OnFragmentInteractionListener,
        RegistrationFragment.OnFragmentInteractionListener {

    public static final String USER_ID_KEY = "userIdKey";

    @InjectPresenter
    SignInPresenter signInPresenter;

    @Inject
    PermissionsHelper permissionsHelper;

    boolean driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
            signInPresenter.onLocationPermissionsResult(permissionsHelper
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
                .replace(R.id.fl_container, AuthenticationFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showRegistrationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, RegistrationFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void checkPermissions() {
        if (permissionsHelper.checkLocationPermission(this)) {
            signInPresenter.locationPermissionsGranted();
        } else {
            signInPresenter.locationPermissionsNotGranted();
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
