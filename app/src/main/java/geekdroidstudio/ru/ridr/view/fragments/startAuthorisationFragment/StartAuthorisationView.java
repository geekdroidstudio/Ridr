package geekdroidstudio.ru.ridr.view.fragments.startAuthorisationFragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface StartAuthorisationView extends MvpView {
    void enterApp();
}