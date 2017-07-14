package com.greenfield.propertyapp.utils;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SOVAVY on 5/5/2017.
 */

public class ServerUtils {

    public boolean urlReachable(String url){
        HttpURLConnection httpConn = sendHttpHEAD(url);
        if (httpConn != null){
            try {
                return (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static String getFullUrl(String url){
        return String.format(Constants.serverAddress + url);
    }


    public HttpURLConnection sendHttpHEAD(String url) {
        String serverUrl = getFullUrl(url);
        HttpURLConnection httpConn = null;
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            httpConn = (HttpURLConnection) new URL(serverUrl).openConnection();
            httpConn.setRequestMethod("HEAD");

            Log.d("urlReachable (HEAD)", serverUrl + ": " + httpConn.getResponseCode());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return httpConn;
    }
}
