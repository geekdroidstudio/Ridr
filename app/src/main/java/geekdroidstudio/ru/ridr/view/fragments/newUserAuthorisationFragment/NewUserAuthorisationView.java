package geekdroidstudio.ru.ridr.view.fragments.newUserAuthorisationFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface NewUserAuthorisationView extends MvpView {
    void finishRegistration();
}