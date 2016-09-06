package com.nice.httpapi.response.dataparser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.model.NiceValue;
import com.nice.model.SignInModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by jiao on 2016/7/31.
 */

public class NiceSignPaser {
    public SignInModel paser2NiceSignValue(JSONObject response){
        Gson gson = QSGsonFactory.create();

        Type type = new TypeToken<NiceValue>() {
        }.getType();

        return gson.fromJson(response.toString(), type);
    }
    public static JSONObject paser2JSONObject(SignInModel niceSignValue) {

        Gson gson = QSGsonFactory.create();
        try {
            return new JSONObject(gson.toJson(niceSignValue));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
