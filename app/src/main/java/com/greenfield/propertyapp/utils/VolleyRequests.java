package com.greenfield.propertyapp.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SOVAVY on 5/5/2017.
 */


public class VolleyRequests {

    public static final String TAG = VolleyRequests.class.getName();
    private static final int MY_SOCKET_TIMEOUT_MS = 50000;
    private Context context;


    public VolleyRequests(Context ctx) {
        this.context = ctx;
    }


    public void JsonObjRequest(String url, final VolleyJsonCallBack callBack) {

        callBack.onStart();
        Log.d(TAG, "Date Url: " + url);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                        callBack.onFinish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        callBack.onError(error);

                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);

    }

    public void JsonPostObjRequest(String url, final VolleyJsonCallBack callBack) {

        callBack.onStart();
        Log.d(TAG, "Date Url: " + url);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callBack.onSuccess(response);
                        callBack.onFinish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        callBack.onError(error);

                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);

    }


    public void strRequest(String url, final VolleyStrCallBack callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        callBack.onSuccess(response);
                        //Log.d(TAG,"Returned data is "+response);
                        // Do something with the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        callBack.onError(error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void postData(String url, Map<String, String> params, final VolleyPostCallBack callBack) {

        callBack.onStart();
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callBack.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callBack.onFinish();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError(error);
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(context).addToRequestQueue(jsonObjRequest);
    }

    public interface VolleyPostCallBack {

        void onSuccess(JSONObject result) throws JSONException;

        void onError(VolleyError error);

        void onStart();

        void onFinish();
    }


    public interface VolleyStrCallBack {


        void onSuccess(String result);

        void onError(VolleyError error);
    }


    public interface VolleyJsonCallBack {

        void onSuccess(JSONObject result);

        void onError(VolleyError error);

        void onStart();

        void onFinish();
    }

    public interface PaginateCallBack {
        void onStart();

        void onProgress(int progress);

        void onSuccess(JSONObject result);

        void onError1(VolleyError error);

        void onError2(VolleyError error);

        void onFinish();

    }


}
