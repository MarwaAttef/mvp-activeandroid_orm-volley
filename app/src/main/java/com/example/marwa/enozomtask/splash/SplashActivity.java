package com.example.marwa.enozomtask.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.marwa.enozomtask.BaseActivity;
import com.example.marwa.enozomtask.R;
import com.example.marwa.enozomtask.util.ActivityUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void startFragment() {
        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (splashFragment == null) {
            // Create the fragment
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashFragment, R.id.contentFrame);
            // Create the presenter
            SplashPresenter   splashPresenter = new SplashPresenter(splashFragment);
        }
    }

    @Override
    protected void setToolBarSearchView() {

    }

    @Override
    protected void setToolbar() {

    }

}
