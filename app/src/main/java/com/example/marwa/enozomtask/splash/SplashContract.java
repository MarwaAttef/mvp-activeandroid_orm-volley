package com.example.marwa.enozomtask.splash;

import com.example.marwa.enozomtask.BasePresenter;
import com.example.marwa.enozomtask.BaseView;

/**
 * Created by marwa on 9/7/17.
 */

public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void navigateToMainActivity();

        void startTimer();
    }

    interface Presenter extends BasePresenter {
        void checkCredentials();
    }
}