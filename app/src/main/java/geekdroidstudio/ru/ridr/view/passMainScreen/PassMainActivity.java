package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.view.fragments.MapFragment;

public class PassMainActivity extends AppCompatActivity
        implements MapFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_activity_main_map_container, MapFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }
}
