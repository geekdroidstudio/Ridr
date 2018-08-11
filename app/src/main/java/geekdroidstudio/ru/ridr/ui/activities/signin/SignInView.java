package geekdroidstudio.ru.ridr.ui.activities.signin;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface SignInView extends MvpView {

    void checkPermissions();

    @StateStrategyType(SingleStateStrategy.class)
    void showErrorPermissionsMsg();

    @StateStrategyType(SingleStateStrategy.class)
    void showAuthorizationFragment();

    @StateStrategyType(SingleStateStrategy.class)
    void showRegistrationFragment();

    void requestPermissions();

    void onDriverSingingIn();

    void onPassengerSingingIn();

    void onSignedIn(String userId);
}