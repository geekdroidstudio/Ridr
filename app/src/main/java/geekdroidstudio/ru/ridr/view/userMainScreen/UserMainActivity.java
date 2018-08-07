package geekdroidstudio.ru.ridr.view.userMainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.UserMainPresenter;
import geekdroidstudio.ru.ridr.view.driverMainScreen.DriverMainActivity;
import geekdroidstudio.ru.ridr.view.fragments.registrationFragment.RegistrationFragment;
import geekdroidstudio.ru.ridr.view.fragments.startAuthenticationFragment.StartAuthenticationFragment;
import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainActivity;

public class UserMainActivity extends MvpAppCompatActivity implements UserMainView,
        StartAuthenticationFragment.OnFragmentInteractionListener,
        RegistrationFragment.OnFragmentInteractionListener {
    @InjectPresenter
    UserMainPresenter userMainPresenter;

    public static final String USER_ID_KEY = "userIdKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
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
    public void launchDriverActivity(String userId) {
        startActivity(new Intent(getApplicationContext(), DriverMainActivity.class).putExtra(USER_ID_KEY, userId));
    }

    @Override
    public void launchPassengerActivity(String userId) {
        startActivity(new Intent(getApplicationContext(), PassengerMainActivity.class).putExtra(USER_ID_KEY, userId));
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
