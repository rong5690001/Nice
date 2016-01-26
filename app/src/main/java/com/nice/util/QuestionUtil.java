package com.nice.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.nice.NiceApplication;
import com.nice.httpapi.response.dataparser.NicetSheetPaser;
import com.nice.model.NicetSheet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2016/1/27.
 */
public class QuestionUtil {

    public static boolean saveQuestion(JSONObject jsonObject) {

        SharedPreferences.Editor editor = NiceApplication.instance().getEditor();
        JSONArray result;
        try {
            result = jsonObject.getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                JSONObject questEntity = result.getJSONObject(i);
                editor.putString(questEntity.getString("shId"), questEntity.toString());
                String shIds = NiceApplication.instance().getPreferences().getString("shIds", "")
                        + "|"
                        + questEntity.getString("shId");
                shIds = TextUtils.isEmpty(shIds) ? questEntity.getString("shId")
                        : shIds + "|" + questEntity.getString("shId");
                editor.putString("shIds", shIds);
            }
            return editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<NicetSheet> getQusetions() {


        SharedPreferences preferences = NiceApplication.instance().getPreferences();
        String shIdsStr = preferences.getString("shIds", "");

        if (TextUtils.isEmpty(shIdsStr)) return null;

        List<NicetSheet> nicetSheetList = new ArrayList<>();
        try {
            if (!shIdsStr.contains("|")) {
                nicetSheetList.add(NicetSheetPaser.paser(new JSONObject(shIdsStr)));
                return nicetSheetList;
            }
            String[] shIds = shIdsStr.split("|");

            for (String shId : shIds) {
                if(preferences.contains(shId)) {
                    nicetSheetList.add(NicetSheetPaser.paser(
                            new JSONObject(preferences.getString(shId, null))));
                }
            }
            return nicetSheetList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}
