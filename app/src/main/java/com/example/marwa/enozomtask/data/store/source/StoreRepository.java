package com.example.marwa.enozomtask.data.store.source;

import android.support.annotation.NonNull;

import com.example.marwa.enozomtask.data.store.Store;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/7/17.
 */

public class StoreRepository implements StoreDataSource {


    private static StoreRepository INSTANCE = null;

    private final StoreDataSource storeRemoteDataSource;
    private final StoreDataSource storeLocalDataSource;


    // Prevent direct instantiation.
    private StoreRepository(@NonNull StoreDataSource tasksRemoteDataSource ,@NonNull StoreDataSource tasksLocalDataSource)  {
        storeRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        storeLocalDataSource = checkNotNull(tasksLocalDataSource);

    }

    public static StoreRepository getInstance(StoreDataSource tasksRemoteDataSource,StoreDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new StoreRepository(tasksRemoteDataSource,tasksLocalDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getStores(@NonNull final StoreRemoteGenericCallback callback) {
        checkNotNull(callback);

    storeLocalDataSource.getStores( new StoreDataSource.StoreRemoteGenericCallback() {
        @Override
        public void onSuccess(ArrayList<Store> stores) {
            if(stores!=null && stores.size()!=0){
            callback.onSuccess(stores);
            }else {
                getStoresRemotly(callback);


            }
        }

        @Override
        public void onServerError() {
// no Server error supposed to be locally
        }

        @Override
        public void onConnectionError() {


        }

        @Override
        public void onAuthenticationError() {


        }

        @Override
        public void onCustomError(@NonNull String store) {


        }
    });

}




    @Override
    public void saveStores(@NonNull ArrayList<Store> stores) {


}



    private void getStoresRemotly(@NonNull final StoreRemoteGenericCallback callback){
        storeRemoteDataSource.getStores( new StoreDataSource.StoreRemoteGenericCallback() {
            @Override
            public void onSuccess(ArrayList<Store> stores) {
                callback.onSuccess(stores);
                storeLocalDataSource.saveStores(stores);
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
            public void onCustomError(@NonNull String store) {
                callback.onCustomError(store);
            }
        });
    }
}