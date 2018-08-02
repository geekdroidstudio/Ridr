package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainView;

@InjectViewState
public class PassengerMainPresenter extends MvpPresenter<PassengerMainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        //  getViewState().showFindDriversFragment();
        getViewState().showMapFragment();
        getViewState().showRouteDataFragment();
    }

    public void routeCreated(List<LatLng> routePoints) {
        getViewState().showRouteInMapFragment(routePoints);
    }
}
