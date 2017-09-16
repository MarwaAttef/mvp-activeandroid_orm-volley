package com.example.marwa.enozomtask;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.marwa.enozomtask.util.DataBaseHelper;

/**
 * Created by marwa on 9/10/17.
 */

public class CustomApplication extends Application {
    private static Context context;
    public void onCreate() {
        super.onCreate();
        CustomApplication.context = getApplicationContext();
initDataBase();
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static Context getAppContext() {
        return CustomApplication.context;
    }
    private void initDataBase() {
        DataBaseHelper databaseHelper = new DataBaseHelper(CustomApplication.this);
        databaseHelper.initDataBase();
    }
}
