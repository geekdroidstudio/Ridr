package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import geekdroidstudio.ru.ridr.view.passengerMainScreen.PassengerMainView;

@InjectViewState
public class PassengerMainPresenter extends MvpPresenter<PassengerMainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showFindDriversFragment();
        //getViewState().showMapFragment();
        //getViewState().showRouteDataFragment();
    }
}
