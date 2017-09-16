package com.example.marwa.enozomtask.data.store.source.locale;

import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.example.marwa.enozomtask.data.store.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwa on 9/12/17.
 */

public class StoresManager {
    public final static int ITEMS_COUNT = 3;
    private static StoresManager instance;

    private StoresManager(){}

    public static StoresManager getInstance(){
        if (instance == null)
            instance = new StoresManager();
        return instance;
    }
    //------------------------- getStores -------------------------------//

    public List<Store> getStores(){
        return new Select().from(Store.class).execute();
    }
//------------------------- saveStores -------------------------------//
    public void saveStores(ArrayList<Store> stores){

        if(stores!=null || stores.size()!=0 ) {

            for (int i = 0; i < stores.size(); i++) {
                stores.get(i).save();
            }
        }
    }

//------------------------- deleteStores -------------------------------//

    public Boolean deleteStores(int itemId){
        From from = new Delete().from(Store.class);
        from.executeSingle();
        return !from.exists();
    }
}
