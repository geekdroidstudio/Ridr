package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import geekdroidstudio.ru.ridr.view.fragments.passengerFindDriversFragment.PassengerFindDriversView;

@InjectViewState
public class PassengerFindDriversPresenter extends MvpPresenter<PassengerFindDriversView> {

        @Override
        protected void onFirstViewAttach() {
            super.onFirstViewAttach();
            getViewState().showMapFragment();
        }
}
