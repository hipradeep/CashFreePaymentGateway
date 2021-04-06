package com.example.cashfreepaymentgateway.retrofit;

import android.util.Log;
import android.widget.Toast;

import com.example.cashfreepaymentgateway.BuildConfig;
import com.google.gson.Gson;


/**
 * Created by padmakar on 25/5/18.
 */

public class LoggerUtil {
    private static final String TAG = "OUTPUT";

    public static void logItem(Object src) {
        Gson gson = new Gson();
        if (BuildConfig.DEBUG)
            Log.e(TAG, "====:> " + gson.toJson(src));
    }


}
