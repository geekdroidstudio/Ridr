package geekdroidstudio.ru.ridr.view.fragments.startAuthenticationFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface StartAuthenticationView extends MvpView {
    void onClickSignIn();

    void onClickSignUp();
}