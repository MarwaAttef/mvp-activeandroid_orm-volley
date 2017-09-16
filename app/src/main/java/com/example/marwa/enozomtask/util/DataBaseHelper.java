package com.example.marwa.enozomtask.util;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.marwa.enozomtask.data.store.Store;

/**
 * Created by marwa on 9/12/17.
 */

public class DataBaseHelper {
    private Context context;

    public DataBaseHelper(Context context) {
        this.context = context;
    }

    public void initDataBase() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(context);
        configurationBuilder.addModelClasses(Store.class);
        ActiveAndroid.initialize(configurationBuilder.create());
    }
}