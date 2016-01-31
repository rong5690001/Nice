package com.nice.httpapi.response.dataparser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.model.NiceQuestion;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 2016/1/31.
 */
public class NiceNewQuestPaser {

    public static List<NiceQuestion> paser(JSONObject jsonObject){
        try {
            String niceQusetion = jsonObject.getJSONArray("result").toString();
            Gson gson = QSGsonFactory.create();
            Type type = new TypeToken<List<NiceQuestion>>(){}.getType();
            return gson.fromJson(niceQusetion, type);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
