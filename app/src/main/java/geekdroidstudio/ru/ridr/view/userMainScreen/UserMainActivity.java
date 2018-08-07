package geekdroidstudio.ru.ridr.view.userMainScreen;

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
    @InjectPresenter
    UserMainPresenter userMainPresenter;

    @Inject
    PermissionsHelper permissionsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        App.getInstance().getComponent().inject(this);
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
    public void launchDriverActivity() {
        startActivity(new Intent(getApplicationContext(), DriverMainActivity.class));
    }

    @Override
    public void launchPassengerActivity() {
        startActivity(new Intent(getApplicationContext(), PassengerMainActivity.class));
    }

    //StartAuthenticationFragment method implementations
    @Override
    public void changeFragmentToRegistration() {
        showRegistrationFragment();
    }

    //RegistrationFragment method implementations
    @Override
    public void startDriverActivity() {
        launchDriverActivity();
    }

    @Override
    public void startPassengerActivity() {
        launchPassengerActivity();
    }
}
