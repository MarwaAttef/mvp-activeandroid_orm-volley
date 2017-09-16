package com.example.marwa.enozomtask.data.store.source.locale;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.activeandroid.query.Select;
import com.example.marwa.enozomtask.Constants;
import com.example.marwa.enozomtask.CustomApplication;
import com.example.marwa.enozomtask.data.store.Store;
import com.example.marwa.enozomtask.data.store.source.StoreDataSource;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/12/17.
 */

public class StoreLocaleDataSource implements StoreDataSource {
    //----------------------singletone ---------------------//
    private static StoreLocaleDataSource INSTANCE;
    private String TAG = "StoreLocaleDataSource";

    public static StoreLocaleDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StoreLocaleDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getStores(@NonNull final StoreDataSource.StoreRemoteGenericCallback callback) {
        checkNotNull(callback);

        StoresManager storesManager = StoresManager.getInstance();

        ArrayList<Store> stores = new ArrayList<Store>(storesManager.getStores());
        if(stores!=null) {
            Log.e(TAG, "getStores:Stores size" + stores.size());
        }
        callback.onSuccess(stores);

    }


    @Override
    public void saveStores(@NonNull ArrayList<Store> stores) {
        StoresManager storesManager = StoresManager.getInstance();
        storesManager.saveStores(stores);
    }


}
