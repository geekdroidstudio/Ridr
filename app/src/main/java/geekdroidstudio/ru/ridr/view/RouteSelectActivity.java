package geekdroidstudio.ru.ridr.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.entity.routes.DualTextRoute;
import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataFragment;

public class RouteSelectActivity extends AppCompatActivity implements
        RouteDataFragment.OnFragmentInteractionListener {

    public static final String START_KEY = "startKey";
    public static final String FINISH_KEY = "finishKey";
    public static final String MULTI_ROUTE_KEY = "routeKey";

    @BindString(R.string.route_data_fragment_tag)
    String routeDataFragmentTag;

    private RouteDataFragment routeDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_select);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            String start = getIntent().getStringExtra(START_KEY);
            String finish = getIntent().getStringExtra(FINISH_KEY);

            RouteDataFragment routeDataFragment = (RouteDataFragment) getFragment(routeDataFragmentTag);
            routeDataFragment.setFields(start, finish);
        }
    }

    @Override
    public void routeCreated(DualTextRoute dualTextRoute, List<LatLng> routePoints) {
        Intent data = new Intent();

        data.putExtra(START_KEY, dualTextRoute.getStart());
        data.putExtra(FINISH_KEY, dualTextRoute.getFinish());
        data.putParcelableArrayListExtra(MULTI_ROUTE_KEY, new ArrayList<>(routePoints));

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
