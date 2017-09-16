package com.example.marwa.enozomtask.data.store;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwa on 9/7/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table(name = "Stores")
public final class Store extends  Model{
    @NonNull
    private final static String TAG = "Store";
    @Nullable
    @JsonProperty("StoreID")
    @Column(name = "StoreId")
    private String StoreId;
    @Nullable
    @JsonProperty("StoreName")
    @Column(name = "storeName")
    private String storeName;
    @Nullable
    @JsonProperty("StoreDescription")
    @Column(name = "storeDescription")
    private String storeDescription;
    @Nullable
    @JsonProperty("StoreLogo")
    @Column(name = "storeLogo")
    private String storeLogo;

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }
    public static ArrayList<Store> serializeFromJson(JSONArray storeJSON) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Store> lstStores = null;
        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createParser(storeJSON.toString());
        TypeReference<List<Store>> tRef = new TypeReference<List<Store>>() {};
        lstStores = mapper.readValue(jp, tRef);

        return lstStores;
    }
}
