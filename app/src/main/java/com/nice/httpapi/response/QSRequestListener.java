package com.nice.httpapi.response;

import android.content.Intent;
import com.android.volley.Response;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/6.
 */
public class QSRequestListener implements Response.Listener<JSONObject> {

    private Response.Listener<JSONObject> childListener;

    public QSRequestListener(Response.Listener<JSONObject> childListener){
        this.childListener = childListener;
    }

    @Override
    public void onResponse(JSONObject response) {
        childListener.onResponse(response);
    }
}
