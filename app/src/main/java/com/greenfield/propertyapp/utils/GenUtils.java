package com.greenfield.propertyapp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by SOVAVY on 5/5/2017.
 */

public class GenUtils {

    public static ArrayList<Object> arrNoGPSCaptureIds = new ArrayList<Object>();
    public static String strDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

    public static <T> T[] extendArray(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String joinArray(String[] arrItems) {
        StringBuilder sb = new StringBuilder();
        for (String n : arrItems) {
            if (sb.length() > 0)
                sb.append(',');
            sb.append(n);
            // sb.append("'").append(n).append("'");
        }
        return sb.toString();
    }

    public static String[] getCursorPropAsArray(Cursor cursor, String fieldName) {
        String[] arrItems = new String[cursor.getCount()];
        int cnt = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrItems[cnt] = cursor.getString(cursor.getColumnIndex(fieldName));
            cnt++;
        }
        return arrItems;
    }

    public static JSONObject getJSONRelatedObjOrNull(JSONObject jsObj, String name) {
        JSONObject jsRelObj = null;

        if (!jsObj.isNull(name)) {
            try {
                jsRelObj = jsObj.getJSONObject(name);
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return jsRelObj;
    }

    public static String getIdFromUrlRes(String url) {
        // Get ID only from resource url. This is on useful for REST api's
        String strId = "";

        String[] parts = url.split("/");
        if (parts.length > 1) {
            strId = parts[parts.length - 1];
            Log.d("getIdFromUrlRes", "ID: " + strId);
        }

        return strId;
    }


    public static boolean gpsIsEnabled(Context ctx) {
        final Context context = ctx;
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;
        else
            return false;
    }

    public static void gpsAlertDialog(Context ctx) {
        final Context context = ctx;

        new AlertDialog.Builder(ctx)
                .setMessage("We need your location for easy delivery. You must enable your GPS to continue!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        }).show();
    }

    public static void enableGPSCheck(Context ctx) {

        if (!gpsIsEnabled(ctx)) {
            gpsAlertDialog(ctx);
        }

    }

    public static String getNowString() {
        String DATE_FORMAT_NOW = strDateTimeFormat;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public static Date string2Date(String strDatetime) {
        Date dtDatetime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(strDateTimeFormat);
        if (strDatetime != null && !strDatetime.isEmpty()) {
            try {
                dtDatetime = sdf.parse(strDatetime);
            } catch (ParseException e) {
                Log.d("string2Date", e.getMessage());
            }
        }
        return dtDatetime;
    }

    public static String Date2string(Date dtDatetime) {
        DateFormat df = new SimpleDateFormat(strDateTimeFormat.replace("T", " "));
        return df.format(dtDatetime);
    }


    public static String getDeviceId(Context context) {
        String id = getUniqueID(context);
        if (id == null)
            id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id;
    }

    private static String getUniqueID(Context context) {

        String telephonyDeviceId = "NoTelephonyId";
        String androidDeviceId = "NoAndroidId";

        // get telephony id
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephonyDeviceId = tm.getDeviceId();
            if (telephonyDeviceId == null) {
                telephonyDeviceId = "NoTelephonyId";
            }
        } catch (Exception e) {
        }

        // get internal android device id
        try {
            androidDeviceId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            if (androidDeviceId == null) {
                androidDeviceId = "NoAndroidId";
            }
        } catch (Exception e) {

        }
        // build up the uuid
        try {
            String id = getStringIntegerHexBlocks(androidDeviceId.hashCode())
                    + "-"
                    + getStringIntegerHexBlocks(telephonyDeviceId.hashCode());

            return id;
        } catch (Exception e) {
            return "0000-0000-1111-1111";
        }
    }

    public static String getStringIntegerHexBlocks(int value) {
        String result = "";
        String string = Integer.toHexString(value);

        int remain = 8 - string.length();
        char[] chars = new char[remain];
        Arrays.fill(chars, '0');
        string = new String(chars) + string;

        int count = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            count++;
            result = string.substring(i, i + 1) + result;
            if (count == 4) {
                result = "-" + result;
                count = 0;
            }
        }

        if (result.startsWith("-")) {
            result = result.substring(1, result.length());
        }

        return result;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    public static String getAppVersion(Context ctx) {
        String strAppVersion = "";
        String strVersion = "";
        int intCode = 0;
        try {
            strVersion = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
            intCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        strAppVersion = intCode + "," + strVersion;

        return strAppVersion;
    }


    public static String makeSlug(String input) {
        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");

        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }


    public static boolean checkPlayServices(Activity activity) {

        int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("checkPlayServices", "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public static String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key).substring(2, obj.getString(key).length() - 2);
//            trimmedString  = obj.get;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

//    public static void createAlert(Context context, String message, String title, String buttonMsg) {
//        new AlertDialogWrapper.Builder(context)
//                .setTitle(title)
//                .setMessage(message)
//                .setNegativeButton(buttonMsg, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
//    }

    public static void CheckIfNullValue(String modelField, TextView textView) {
        if (modelField.equals("null")) {
            textView.setText(Constants.EMPTY_STRING);
        } else {
            textView.setText(modelField);
        }
    }

    public static double formatingCost(double data) {
        DecimalFormat df = new DecimalFormat("#.##");

        return Double.valueOf(df.format(data));
    }

    public static boolean isEmpty(EditText textView, TextInputLayout textInputLayout, String errorMessage) {

        if (textView.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(errorMessage);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public static void getToastMessage(Context context, String messsage) {
        try {
            Toast.makeText(context, messsage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("Toast Message Error", e.getMessage());
        }
    }


    public static double[] getGPSCoords(Context ctx) {
//		String gpsValues = "0.0,0.0";
        double[] gpsValues = {0, 0};
        double longitude, latitude;

        try {
            LocationManager lm = (LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                gpsValues[0] = location.getLatitude();
                gpsValues[1] = location.getLongitude();
            }



//			gpsValues = String.valueOf(latitude) + "," + String.valueOf(longitude);
        } catch (Exception e) {
            Log.d("getGPSCoords", e.getMessage() + ".");
        }

        return gpsValues;

    }

    public static ArrayAdapter<CharSequence> getCharSequenceArrayAdapter(Activity activity, int array, int layout) {
        return ArrayAdapter.createFromResource(activity, array, layout);
    }

    private void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}

