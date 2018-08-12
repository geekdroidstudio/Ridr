package ru.geekdroidstudio.ridr.ui.activities.userbase;

import android.annotation.SuppressLint;
import android.location.Location;

import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekdroidstudio.ridr.model.EmulateGeo;
import ru.geekdroidstudio.ridr.model.authentication.AuthDatabase;
import ru.geekdroidstudio.ridr.model.communication.entity.User;
import ru.geekdroidstudio.ridr.model.repository.Repository;
import timber.log.Timber;

public abstract class UserBasePresenter<T extends UserBaseView> extends MvpPresenter<T> {

    @Inject
    Repository repository;

    @Inject
    EmulateGeo emulateGeo;

    protected final Scheduler scheduler;
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    AuthDatabase authDatabase;

    public UserBasePresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setUserId(String id) {
        authDatabase.setListener(userName -> {
            getUser().setName(userName);
            getViewState().setUserName(userName);
        });
        authDatabase.getUserName(id);

        //runRealGeo();
        runEmulateGeo();
    }

    public abstract User getUser();

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }


    public void locationErrorResolve() {
        compositeDisposable.add(startListenGeo());
    }

    public void locationErrorNotResolve() {
        getViewState().showLocationSettingsError();
    }

    protected void runRealGeo() {
        checkLocationServices();
    }

    private void runEmulateGeo() {
        compositeDisposable.add(emulateGeo.getSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(this::onLocationChanged, Timber::e));
    }

    protected abstract void onLocationChanged(Location location);

    private Disposable startListenGeo() {
        return repository.startListenLocation()
                .subscribe(this::onLocationChanged, Timber::e);
    }

    @SuppressLint("CheckResult")
    private void checkLocationServices() {
        repository.checkLocationResponse()
                .subscribe(() -> compositeDisposable.add(startListenGeo()),
                        throwable -> {
                            if (!(throwable instanceof ApiException)) {
                                getViewState().showLocationSettingsError();
                                Timber.e(throwable);
                                return;
                            }
                            ApiException apiException = (ApiException) throwable;
                            if (apiException.getStatusCode() != LocationSettingsStatusCodes
                                    .RESOLUTION_REQUIRED) {
                                getViewState().showLocationSettingsError();
                                Timber.e(throwable);
                                return;
                            }
                            getViewState().resolveLocationException(apiException);
                        });
    }
}
