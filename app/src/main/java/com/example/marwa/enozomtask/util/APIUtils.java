package com.example.marwa.enozomtask.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marwa.enozomtask.Constants;
import com.google.common.base.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by marwa on 9/7/17.
 */

public class APIUtils {
    private final static String TAG = "APIUtils";
    private RequestQueue mRequestQueue;
    private static APIUtils sInstance;
    private Context ctx;
    public static final int POST_METHOD =  Request.Method.POST;
    public static final int GET_METHOD =  Request.Method.GET;
    public static final int DELETE_METHOD =  Request.Method.DELETE;
    public static final int PUT_METHOD =  Request.Method.PUT;


    private APIUtils(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized APIUtils getInstance(@NonNull Context ctx) {
        checkNotNull(ctx);
        if (sInstance == null) {
            sInstance = new APIUtils(ctx);
        }
        return sInstance;
    }

    public void requestJsonObject(@NonNull int method, @NonNull String strUrl, @Nullable HashMap<String, String> params, @NonNull final boolean useApiKey, @Nullable final String apiKey, final VolleyCallback callback) {
        checkNotNull(strUrl);
        checkNotNull(useApiKey);
        Log.d(TAG, "***************************** requestJsonObject *****************************");
        final String strURL = Constants.API_DOMAIN + strUrl;
        Log.d(TAG, "requestJsonObject:url " + strURL);
        Log.d(TAG, "requestJsonObject:method " + method);

        try {
            JSONObject jsonRequest = null;
            if (params != null) {
                Log.e(TAG, params.toString());
                jsonRequest = new JSONObject(params);
            }
            Log.d(TAG, "requestJsonObject:jsonRequest " + jsonRequest);
            final JsonObjectRequest req = new CustomJsonObjectRequest(method, strURL, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "requestJsonObject:response " + response.toString());
                            callback.onSuccess(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            if (error != null && !Strings.isNullOrEmpty(error.getMessage())) {
                                callback.onConnectionError();
                                Log.e(TAG, "requestJsonObject:response:onErrorResponse:volleyError " + error.getMessage());
                            } else if (response != null && response.data != null) {
                                switch (response.statusCode) {
                                    case 500:
                                        callback.onServerError();
                                        break;
                                    case 400:
                                        callback.onAuthenticationError();
                                        break;
                                    default:
                                        callback.onConnectionError();
                                        break;
                                }
                                Log.e(TAG, "requestJsonObject:response.statusCode:onErrorResponse " + response.statusCode);
                            } else {
                                callback.onConnectionError();
                                Log.e(TAG, "requestJsonObject:response:onErrorResponse:Connection Error ");
                            }

                        }
                    })


            {

                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> mHeaders = new ArrayMap<>();
                    if (useApiKey && !Strings.isNullOrEmpty(apiKey)) {
                        mHeaders.put("Authorization", "Bearer " + apiKey);
                        Log.d(TAG, "requestJsonObject:getHeaders:Authorization " + "Bearer " + apiKey);
                    }
                    return mHeaders;
                }

            };
            addToRequestQueue(req);
        } catch (Exception e) {
            if (!Strings.isNullOrEmpty(e.getMessage())) {
                Log.e(TAG, "requestJsonObject:Exception " + e.getMessage());
            }
        }
    }

    public void requestJsonArray(@NonNull int method,@NonNull String strUrl,@Nullable HashMap<String, String> params, @NonNull final boolean useApiKey, @Nullable final String apiKey, final VolleyCallbackArray callback) {
        checkNotNull(strUrl);
        Log.d(TAG, "***************************** requestJsonArray *****************************");
        final String strURL = Constants.API_DOMAIN + strUrl;
        Log.d(TAG, "requestJsonArray:url " + strURL);
        Log.d(TAG, "requestJsonArray:method " + method);
        try {
            JSONObject jsonRequest = null;
            if (params != null) {
                jsonRequest = new JSONObject(params);
            }
            Log.d(TAG, "requestJsonArray:jsonRequest " + jsonRequest);
            final JsonArrayRequest req = new CustomJsonArrayRequest(method, strURL, new JSONArray().put(jsonRequest),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, "requestJsonArray:response " + response.toString());
                            callback.onSuccess(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            if (error != null && !Strings.isNullOrEmpty(error.getMessage())) {
                                callback.onConnectionError();
                                Log.e(TAG, "requestJsonArray:responseon:ErrorResponse:volleyError " + error.getMessage());
                            } else if (response != null && response.data != null) {
                                switch (response.statusCode) {
                                    case 500:
                                        callback.onServerError();
                                        break;
                                    case 400:
                                        callback.onAuthenticationError();
                                        break;
                                    default:
                                        callback.onConnectionError();
                                        break;
                                }
                                Log.e(TAG, "requestJsonArray:response.statusCode " + response.statusCode);
                            } else {
                                callback.onConnectionError();
                            }
                        }
                    })

            {
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> mHeaders = new ArrayMap<>();
                    if (useApiKey && !Strings.isNullOrEmpty(apiKey)) {
                        mHeaders.put("Authorization", "Bearer " + apiKey);
                        Log.d(TAG, "requestJsonArray:getHeaders:Authorization " + "Bearer " + apiKey);
                    }
                    return mHeaders;
                }

            };

            addToRequestQueue(req);
        } catch (Exception e) {
            if (!Strings.isNullOrEmpty(e.getMessage())) {
                Log.e(TAG, "requestJsonArray:Exception " + e.getMessage());
            }
        }
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(req);
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx);
        }

        return mRequestQueue;
    }

    public interface VolleyCallback {
        void onSuccess(@Nullable JSONObject result);

        void onServerError();

        void onConnectionError();

        void onAuthenticationError();

        void onCustomError(@NonNull String message);
    }

    public interface VolleyCallbackArray {
        void onSuccess(@Nullable JSONArray result);

        void onServerError();

        void onConnectionError();

        void onAuthenticationError();

        void onCustomError(@NonNull String message);
    }

    public class CustomJsonObjectRequest extends JsonObjectRequest {

        public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                                       Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String json = new String(response.data, "UTF-8");
                if (json.length() == 0) {
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                } else if (isInt(json)) {
                    return Response.success(new JSONObject("{\"response\":" + json + "}"), HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return super.parseNetworkResponse(response);
                }
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException e) {
                return Response.error(new ParseError(e));
            }
        }

        private boolean isInt(String json) {
            try {
                Integer.parseInt(json);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public class CustomJsonArrayRequest extends JsonArrayRequest {

        public CustomJsonArrayRequest(int method, String url, JSONArray jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            try {
                String json = new String(response.data, "UTF-8");

                if (json.length() == 0) {
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return super.parseNetworkResponse(response);
                }
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }
    }

}

