package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.view.passMainScreen.PassMainView;
//import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PassMainActivityPresenter extends MvpPresenter<PassMainView> {
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showMapFragment();
        getViewState().showRouteDataFragment();
    }


    public void routeCreated(List<LatLng> routePoints) {
        getViewState().showRouteInMapFragment(routePoints);
    }
}
