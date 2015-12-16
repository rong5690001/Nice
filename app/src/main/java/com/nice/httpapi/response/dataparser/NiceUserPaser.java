package com.nice.httpapi.response.dataparser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.model.NiceUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 2015/12/16.
 */
public class NiceUserPaser {

    public static List<NiceUser> paserNiceUser(JSONObject response){

        try {
            Gson gson = QSGsonFactory.create();

            String people = response.getJSONArray("result").toString();
            Type type = new TypeToken<List<NiceUser>>() {
            }.getType();

            return gson.fromJson(people, type);

        } catch (JSONException e) {
            return null;
        }


    }


}
