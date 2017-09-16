package com.example.marwa.enozomtask.Main;

import android.support.annotation.NonNull;

import com.example.marwa.enozomtask.data.store.Store;
import com.example.marwa.enozomtask.data.store.source.StoreDataSource;
import com.example.marwa.enozomtask.data.store.source.StoreRepository;
import com.example.marwa.enozomtask.splash.SplashContract;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/7/17.
 */

public class MainPresenter implements MainContract.Presenter {
    private final StoreRepository storeRepository;
    private final MainContract.View storeView;
    private final String TAG = "storePresenter";

    public MainPresenter(@NonNull StoreRepository tasksRepository,  @NonNull MainContract.View tasksView) {
        storeRepository = checkNotNull(tasksRepository, "tasksView cannot be null!");
        storeView = checkNotNull(tasksView, "tasksView cannot be null!");
        storeView.setPresenter(this);
    }
    @Override
    public void getStores() {
        storeView.setLoadingIndicator(true);


        storeRepository.getStores(new StoreDataSource.StoreRemoteGenericCallback() {
            @Override
            public void onSuccess(ArrayList<Store> stores) {
                storeView.setLoadingIndicator(false);
//                showEmptyError
                if(stores == null || stores.size()==0){
                    storeView.showEmptyStoreError();
                }
                else {
                //else
                storeView.setStores(stores);
            }}

            @Override
            public void onServerError() {
                storeView.setLoadingIndicator(false);

            }

            @Override
            public void onConnectionError() {
                storeView.setLoadingIndicator(false);

            }

            @Override
            public void onAuthenticationError() {
                storeView.setLoadingIndicator(false);

            }

            @Override
            public void onCustomError(@NonNull String store) {
                storeView.setLoadingIndicator(false);
            }

        });
    }




    @Override
    public void start() {

    }
}
