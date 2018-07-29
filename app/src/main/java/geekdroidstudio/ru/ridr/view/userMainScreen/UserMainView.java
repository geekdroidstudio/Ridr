package geekdroidstudio.ru.ridr.view.userMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface UserMainView extends MvpView {
    void showStartAuthorisationFragment();
    void showRegistrationFragment();
    void launchDriverActivity();
    void launchPassengerActivity();
}