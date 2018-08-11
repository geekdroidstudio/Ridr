package ru.geekdroidstudio.ridr.ui.fragments.signin;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SignInView extends MvpView {
    void onClickSignInPassenger();

    void onClickSignInDriver();

    void onClickSignUp();
}