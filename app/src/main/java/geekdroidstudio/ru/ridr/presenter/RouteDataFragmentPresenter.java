package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.location.places.AutocompletePrediction;

import geekdroidstudio.ru.ridr.view.fragments.routeDataFragment.RouteDataView;

@InjectViewState
public class RouteDataFragmentPresenter extends MvpPresenter<RouteDataView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();

    }

    public void onStartPointTextChange(String pointName) {

    }

    public void onEndPointTextChange(String pointName) {

    }

    public void onStartPointSelected(AutocompletePrediction item) {


    }

    public void onEndPointTextSelected(AutocompletePrediction item) {

    }
}
