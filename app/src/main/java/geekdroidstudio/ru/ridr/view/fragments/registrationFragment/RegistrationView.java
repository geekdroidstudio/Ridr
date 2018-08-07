package geekdroidstudio.ru.ridr.view.fragments.registrationFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RegistrationView extends MvpView {
    void chooseRoleDriver();

    void chooseRolePassenger();
}
