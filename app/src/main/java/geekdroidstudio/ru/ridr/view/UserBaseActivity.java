package geekdroidstudio.ru.ridr.view;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;

import javax.inject.Inject;

import geekdroidstudio.ru.ridr.model.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.presenter.UserBasePresenter;

public abstract class UserBaseActivity<T extends UserBasePresenter<? extends UserBaseView>>
        extends MvpAppCompatActivity implements UserBaseView, AuthDatabase.IAuthDatabase {

    private static final int REQUEST_CHECK_SETTINGS = 333;

    @Inject
    AuthDatabase authDatabase;//TODO: в презентер

    protected void loadUserName(String userId) {
        authDatabase.setContext(this);
        authDatabase.getUserName(userId);
    }

    @Override
    public void wasGetUserName(String userName) {
        setTitle(userName);
    }

    @Override
    public void showLocationSettingsError() {

    }

    public abstract T getPresenter();

    @Override
    public void resolveLocationException(ApiException exception) {
        try {
            ResolvableApiException resolvable = (ResolvableApiException) exception;
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvable.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
        } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
        } catch (ClassCastException e) {
            // Ignore, should be an impossible error.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == Activity.RESULT_OK) {
                    getPresenter().locationErrorResolve();
                } else {
                    getPresenter().locationErrorNotResolve();
                }
                break;
            }
            default: {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Nullable
    protected Fragment getFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
}
