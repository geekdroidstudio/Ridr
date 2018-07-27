package geekdroidstudio.ru.ridr.view.userMainScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.UserMainPresenter;
import geekdroidstudio.ru.ridr.view.fragments.startAuthorisationFragment.StartAuthorisationFragment;

public class UserMainActivity extends MvpAppCompatActivity implements UserMainView, StartAuthorisationFragment.OnFragmentInteractionListener {
    @InjectPresenter
    UserMainPresenter userMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
    }

    @Override
    public void showStartAuthorisationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_user_main_frame, StartAuthorisationFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
