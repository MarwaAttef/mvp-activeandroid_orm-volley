package com.example.marwa.enozomtask;

/**
 * Created by marwa on 9/10/17.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showServerErrorMessage();

    void showAuthenticationErrorMessage();

    void showConnectionErrorMessage();

    void showMessage(String message);
}