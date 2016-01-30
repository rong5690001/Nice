package com.nice.httpapi.response.dataparser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.model.NiceUser;
import com.nice.model.NicetSheet;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 2016/1/30.
 */
public class NiceSheetPaser {

    public static List<NicetSheet> paser(JSONObject response){

        try {
            Gson gson = QSGsonFactory.create();

            String niceSheet = response.getJSONArray("result").toString();
            Type type = new TypeToken<List<NicetSheet>>() {
            }.getType();

            return gson.fromJson(niceSheet, type);

        } catch (JSONException e) {
            return null;
        }


    }
}
