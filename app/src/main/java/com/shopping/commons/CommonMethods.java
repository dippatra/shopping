package com.shopping.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

/**
 * Created by Sang.24 on 12/28/2017.
 */

public class CommonMethods {
    private static final String TAG="CommonMethods";
    private static final int PREFTYPE_STRING = 0;
    private static final int PREFTYPE_INT = 1;
    private static final int PREFTYPE_BOOLEAN = 2;

    public static boolean isOnline(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    // Store string values in Preferences
    public static void savePreferences(Context context, String strKey, String strValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(strKey, strValue);
            editor.commit();
        } catch (Exception ex) {
            Log.e(TAG,ex.toString());
        }
    }

    // Store boolean values in Preferences
    public static void savePreferences(Context context, String strKey, Boolean blnValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(strKey, blnValue);
            editor.commit();
        }catch (Exception ex) {
            Log.e(TAG,ex.toString());
        }
    }
    // Store integer values in Preferences
    public static void savePreferences(Context context, String strKey, int intValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(strKey, intValue);
            editor.commit();
        } catch (Exception ex) {
            Log.e(TAG,ex.toString());
        }
    }

    public static Object getPreferences(Context context, String key, int preferenceDataType) {
        Object value = null;
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences.contains(key)) {
                switch (preferenceDataType) {
                    case PREFTYPE_BOOLEAN:
                        value = sharedPreferences.getBoolean(key, false);
                        break;
                    case PREFTYPE_INT:
                        value = sharedPreferences.getInt(key, 0);
                        break;
                    case PREFTYPE_STRING:
                        value = sharedPreferences.getString(key, "");
                        break;

                }
            }
        } catch (Exception ex) {
            Log.e(TAG,ex.toString());
            return null;
        }
        return value;
    }
    public static void saveShoppingData(Context context,String shoppingData){
        savePreferences(context,"ShoppingData",shoppingData);
    }
    public static JSONObject getShoppingData(Context context){
        Object data;
        try{
            data=getPreferences(context,"ShoppingData",PREFTYPE_STRING);
            if(data!=null){
                return new JSONObject(String.valueOf(data));
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return null;
    }

    public static String performGet(String requestURL) throws Exception {
        String responseBody = "";
        HttpClient httpClient;
        HttpGet httpGet;
        ResponseHandler<String> responseHandler;
        HttpParams httpParams;
        try {
            httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 2 * 60 * 1000);// 2 min
            HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);// 2
            httpClient = new DefaultHttpClient(httpParams);
            httpGet = new HttpGet(requestURL);
            responseHandler = new BasicResponseHandler();
            responseBody = httpClient.execute(httpGet, responseHandler);
        } catch (Exception ex) {
            throw ex;
        }
        return responseBody;
    }

}
