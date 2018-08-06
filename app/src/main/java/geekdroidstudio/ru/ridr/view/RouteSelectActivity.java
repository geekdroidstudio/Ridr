package geekdroidstudio.ru.ridr.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataFragment;

public class RouteSelectActivity extends AppCompatActivity implements
        RouteDataFragment.OnFragmentInteractionListener {

    public static final String START_KEY = "startKey";
    public static final String FINISH_KEY = "finishKey";
    public static final String ROUTE_KEY = "routeKey";
    @BindString(R.string.route_data_fragment_tag)
    String routeDataFragmentTag;
    private RouteDataFragment routeDataFragment;
    private String start;
    private String finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_select);

        ButterKnife.bind(this);

        start = getIntent().getStringExtra(START_KEY);
        finish = getIntent().getStringExtra(FINISH_KEY);

        routeDataFragment = (RouteDataFragment) getFragment(routeDataFragmentTag);
        routeDataFragment.setFields(start, finish);
    }

    @Override
    public void routeCreated(String start, String finish, List<LatLng> routePoints) {
        Intent data = new Intent();

        data.putExtra(START_KEY, start);
        data.putExtra(START_KEY, finish);
        data.putExtra(ROUTE_KEY, routePoints.toArray());

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void hideKeyboard(IBinder windowToken) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null) {
            in.hideSoftInputFromWindow(windowToken, 0);
        }
    }


    private Fragment getFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
