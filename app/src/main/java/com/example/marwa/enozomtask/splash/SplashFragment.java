package com.example.marwa.enozomtask.splash;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marwa.enozomtask.BaseFragment;
import com.example.marwa.enozomtask.Main.MainActivity;
import com.example.marwa.enozomtask.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.common.base.Preconditions.checkNotNull;


public class SplashFragment extends BaseFragment implements SplashContract.View {


    private SplashContract.Presenter presenter;
    private final long SPLASH_DELAY = 2000; // msec

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        return root;
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_WIFI_SETTINGS_FLAG) {
            startTimer();
        }
    }

    @Override
    public void navigateToMainActivity() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }
    }

    @Override
    public void startTimer() {
        if (isNetworkAvailable()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    presenter.checkCredentials();
                }
            }, SPLASH_DELAY);
        } else {
            showNotConnectedAlert();
        }
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);

    }

    @Override
    public void showServerErrorMessage() {

    }

    @Override
    public void showAuthenticationErrorMessage() {

    }

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }
}