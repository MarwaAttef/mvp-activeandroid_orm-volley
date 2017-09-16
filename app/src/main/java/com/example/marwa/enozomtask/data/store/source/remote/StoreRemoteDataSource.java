package com.example.marwa.enozomtask.data.store.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.marwa.enozomtask.Constants;
import com.example.marwa.enozomtask.CustomApplication;
import com.example.marwa.enozomtask.data.store.Store;
import com.example.marwa.enozomtask.data.store.source.StoreDataSource;
import com.example.marwa.enozomtask.util.APIUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/7/17.
 */

public class StoreRemoteDataSource implements StoreDataSource {
    //----------------------singletone ---------------------//
    private static StoreRemoteDataSource INSTANCE;
    private String TAG = "StoreRemoteDataSource";

    public static StoreRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StoreRemoteDataSource();
        }
        return INSTANCE;
    }



    @Override
    public void getStores( @NonNull final StoreRemoteGenericCallback callback) {
        checkNotNull(callback);

        APIUtils.getInstance(CustomApplication.getAppContext()).requestJsonArray(APIUtils.GET_METHOD, Constants.STORE_URL, null, false, null, new APIUtils.VolleyCallbackArray() {

            @Override
            public void onSuccess(@Nullable JSONArray result) {
                if (result != null) {
                    try {
                        ArrayList<Store> stores = Store.serializeFromJson(result);
                        callback.onSuccess(stores);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onServerError() {
                callback.onServerError();
            }

            @Override
            public void onConnectionError() {
                callback.onConnectionError();
            }

            @Override
            public void onAuthenticationError() {
                callback.onAuthenticationError();
            }

            @Override
            public void onCustomError(@NonNull String message) {
                checkNotNull(message);
                callback.onCustomError(message);
            }
        });
    }

    @Override
    public void saveStores(@NonNull ArrayList<Store> stores) {

    }


}
