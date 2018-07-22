package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.presenter.PassMainActivityPresenter;
import geekdroidstudio.ru.ridr.view.fragments.MapFragment;

public class PassMainActivity extends MvpAppCompatActivity implements PassMainView,
        MapFragment.OnFragmentInteractionListener {
    @InjectPresenter
    PassMainActivityPresenter passMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_main_map_container, MapFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
