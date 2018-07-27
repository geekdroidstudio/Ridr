package geekdroidstudio.ru.ridr.view.userMainScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.UserMainPresenter;
import geekdroidstudio.ru.ridr.view.fragments.newUserAuthorisationFragment.NewUserAuthorisationFragment;

public class UserMainActivity extends MvpAppCompatActivity implements UserMainView, NewUserAuthorisationFragment.OnFragmentInteractionListener {
    @InjectPresenter
    UserMainPresenter userMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
    }

    @Override
    public void showNewUserAuthorisationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_user_main_frame, NewUserAuthorisationFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
