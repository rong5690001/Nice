package com.nice.httpapi.response.dataparser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.model.NiceUser;
import com.nice.model.NiceValue;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 2016/1/31.
 */
public class NiceValuePaser {

    public static NiceValue paser2NiceValue(JSONObject response) {

        Gson gson = QSGsonFactory.create();

        Type type = new TypeToken<NiceValue>() {
        }.getType();

        return gson.fromJson(response.toString(), type);
    }

    public static JSONObject paser2JSONObject(NiceValue niceValue) {

        Gson gson = QSGsonFactory.create();
        try {
            return new JSONObject(gson.toJson(niceValue));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
