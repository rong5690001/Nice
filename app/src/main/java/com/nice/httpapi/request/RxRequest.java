package com.nice.httpapi.request;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nice.NiceApplication;
import com.nice.httpapi.response.MetadataParser;

import org.json.JSONObject;
import java.util.HashMap;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2015/11/17.
 */
public class RxRequest {

    public static Observable<JSONObject> createJsonRequest(final int method, final String url, final JSONObject jsonRequest){
        System.out.println("url:" + url);
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(final Subscriber<? super JSONObject> subscriber) {

                QSJsonObjectRequest jsonObjectRequest = new QSJsonObjectRequest(method, url, jsonRequest, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (MetadataParser.hasError(response)){
                            //errorCode became the msg
                            Throwable errorCode = new Throwable(String.valueOf(MetadataParser.getError(response)));
                            subscriber.onError(errorCode);
                            subscriber.onCompleted();
                        }
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NiceApplication.instance(), "网络异常", Toast.LENGTH_SHORT).show();
                        Throwable errorCode = new Throwable(error.getMessage(), error);
                        subscriber.onError(errorCode);
                        subscriber.onCompleted();
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueueManager.INSTANCE.getQueue().add(jsonObjectRequest);
            }
        });
    }

    public static Observable<JSONObject> createIdRequest(final String url, final String _id){
        HashMap<String, String> map = new HashMap<>();
        map.put("_id", _id);
        return createJsonRequest(Request.Method.POST, url, new JSONObject(map));
    }
}
