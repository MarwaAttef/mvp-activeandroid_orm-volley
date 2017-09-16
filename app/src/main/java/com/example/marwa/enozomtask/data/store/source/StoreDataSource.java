package com.example.marwa.enozomtask.data.store.source;

import android.support.annotation.NonNull;

import com.example.marwa.enozomtask.data.store.Store;

import java.util.ArrayList;

/**
 * Created by marwa on 9/7/17.
 */

public interface StoreDataSource {


    interface StoreRemoteGenericCallback {
        void onSuccess(ArrayList<Store> store);

        void onServerError();

        void onConnectionError();

        void onAuthenticationError();

        void onCustomError(@NonNull String store);
    }
    

    void getStores( @NonNull StoreRemoteGenericCallback callback);
    void saveStores(@NonNull ArrayList<Store> stores );
}
