package com.example.marwa.enozomtask.splash;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/7/17.
 */

public class SplashPresenter implements SplashContract.Presenter{
    private final String TAG = "SplashPresenter";
    private final SplashContract.View splashView;

    public SplashPresenter( @NonNull SplashContract.View splashView) {
        this.splashView = checkNotNull(splashView, "tasksView cannot be null!");
        this.splashView.setPresenter(this);
    }
    @Override
    public void start() {
        splashView.startTimer();
    }

    @Override
    public void checkCredentials() {
        splashView.navigateToMainActivity();
    }
}
