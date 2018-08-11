package geekdroidstudio.ru.ridr.ui.fragments.authentication;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AuthenticationView extends MvpView {
    void onClickSignInPassenger();

    void onClickSignInDriver();

    void onClickSignUp();
}