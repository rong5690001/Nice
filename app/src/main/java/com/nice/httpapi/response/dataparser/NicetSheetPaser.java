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
 * Created by chen on 2016/1/27.
 */
public class NicetSheetPaser {


    public static NicetSheet paser(JSONObject response){

            Gson gson = QSGsonFactory.create();

            String nicetSheet = response.toString();
            Type type = new TypeToken<NicetSheet>() {
            }.getType();

            return gson.fromJson(nicetSheet, type);


    }
}
