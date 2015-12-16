package com.nice.httpapi.response.error;

import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2015/2/28.
 */
public class QSResponseErrorListener implements Response.ErrorListener {

    Response.ErrorListener listener;

    public QSResponseErrorListener() {

    }

    public QSResponseErrorListener(Response.ErrorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //向BaseActivity发广播
        System.out.println("error" + error.getMessage());
        if (null != listener)
            listener.onErrorResponse(error);
    }
}
