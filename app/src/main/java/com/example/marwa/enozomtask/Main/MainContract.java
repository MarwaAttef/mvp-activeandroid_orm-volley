package com.example.marwa.enozomtask.Main;

import com.example.marwa.enozomtask.BasePresenter;
import com.example.marwa.enozomtask.BaseView;
import com.example.marwa.enozomtask.data.store.Store;

import java.util.ArrayList;

/**
 * Created by marwa on 9/7/17.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void setStores(ArrayList<Store> stores);

        void showEmptyStoreError();


    }

    interface Presenter extends BasePresenter {
        void getStores();
    }
}
