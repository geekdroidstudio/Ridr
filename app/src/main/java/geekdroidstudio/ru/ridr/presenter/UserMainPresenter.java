package geekdroidstudio.ru.ridr.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainView;

@InjectViewState
public class UserMainPresenter extends MvpPresenter<UserMainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showStartAuthorisationFragment();
    }
}