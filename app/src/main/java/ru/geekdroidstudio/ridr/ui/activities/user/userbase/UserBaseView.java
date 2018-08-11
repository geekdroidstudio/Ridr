package ru.geekdroidstudio.ridr.ui.activities.user.userbase;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.ApiException;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface UserBaseView extends MvpView {

    void showLocationSettingsError();

    void resolveLocationException(ApiException apiException);

    void setUserName(String userName);
}
