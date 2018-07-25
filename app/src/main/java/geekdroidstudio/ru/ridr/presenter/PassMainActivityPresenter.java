package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import geekdroidstudio.ru.ridr.view.passMainScreen.PassMainView;

@InjectViewState
public class PassMainActivityPresenter extends MvpPresenter<PassMainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showMapFragment();
        getViewState().showRouteDataFragment();
    }
}
