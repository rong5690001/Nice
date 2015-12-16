package com.nice.httpapi.response.error;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import de.greenrobot.event.EventBus;

/**
 * Created by zenan on 1/2/15.
 */
public class ErrorHandler {

    private static final String TAG = ErrorHandler.class.getSimpleName();

    public static void handle(Context context, String errorCode){
        handle(context, Integer.parseInt(errorCode));
    }

    public static void handle(Context context, int errorCode) {
        switch (errorCode) {

        }
    }
}
