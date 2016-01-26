package com.nice.util;

import android.content.SharedPreferences;

import com.nice.NiceApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chen on 2016/1/27.
 */
public class QuestionUtil {

    public static boolean saveQuestion(JSONObject jsonObject){

        SharedPreferences.Editor editor = NiceApplication.instance().getEditor();
        JSONArray result;
        try {
            result = jsonObject.getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                JSONObject questEntity = result.getJSONObject(i);
                editor.putString(questEntity.getString("shId"), questEntity.toString());
            }
            return editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }


}
