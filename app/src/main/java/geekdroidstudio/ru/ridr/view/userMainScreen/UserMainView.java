package geekdroidstudio.ru.ridr.view.userMainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface UserMainView extends MvpView {

    void checkPermissions();

    @StateStrategyType(SingleStateStrategy.class)
    void showErrorPermissionsMsg();

    @StateStrategyType(SingleStateStrategy.class)
    void showStartAuthorisationFragment();


    @StateStrategyType(SingleStateStrategy.class)
    void showRegistrationFragment();

    void launchDriverActivity(String userId);

    void launchPassengerActivity(String userId);

    void requestPermissions();
}