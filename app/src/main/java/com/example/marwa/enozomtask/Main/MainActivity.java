package com.example.marwa.enozomtask.Main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import com.example.marwa.enozomtask.BaseActivity;
import com.example.marwa.enozomtask.CustomApplication;
import com.example.marwa.enozomtask.R;
import com.example.marwa.enozomtask.data.store.source.StoreRepository;
import com.example.marwa.enozomtask.data.store.source.locale.StoreLocaleDataSource;
import com.example.marwa.enozomtask.data.store.source.remote.StoreRemoteDataSource;
import com.example.marwa.enozomtask.util.ActivityUtils;

public class MainActivity extends BaseActivity {
   private MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void startFragment() {
         mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
             mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.contentFrame);
            // Create the presenter
            MainPresenter     mainPresenter = new MainPresenter(StoreRepository.getInstance(StoreRemoteDataSource.getInstance(), StoreLocaleDataSource.getInstance()), mainFragment);
        }
    }

    @Override
    protected void setToolBarSearchView() {
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) findViewById(R.id.search_viwe);
        searchView.clearFocus();

        EditText searchEditText = (EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(CustomApplication.getAppContext(),R.color.colorWhite));
        searchEditText.setHintTextColor(ContextCompat.getColor(CustomApplication.getAppContext(),R.color.colorAccent));
        ImageView searchMagIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchMagIcon.setImageResource(android.R.color.transparent);

        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainFragment.setfilter(newText);
                return false;
            }
        });
    }

    @Override
    protected void setToolbar() {
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
