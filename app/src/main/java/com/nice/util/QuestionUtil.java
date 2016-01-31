package com.nice.util;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.nice.NiceApplication;
import com.nice.httpapi.response.dataparser.NiceValuePaser;
import com.nice.httpapi.response.dataparser.NicetSheetPaser;
import com.nice.model.NiceValue;
import com.nice.model.NicetSheet;
import com.nice.model.NicetSheetQuestionGroup;
import com.nice.model.ValueJsonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chen on 2016/1/27.
 */
public class QuestionUtil {

    private static final String QUEST_IDS = "quest_ids";//问卷ID
    private static final String VALUE_IDS = "value_ids";//题目ID

    /**
     * 保存问卷
     *
     * @param jsonObject
     * @return
     */
    public static boolean saveQuestion(JSONObject jsonObject) {
        SharedPreferences preferences = NiceApplication.instance().getQuestPreferencesQuest();
        SharedPreferences.Editor editor = NiceApplication.instance().getQuestEditor();
        JSONArray result;
        try {
            result = jsonObject.getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                JSONObject questEntity = result.getJSONObject(i);
                if (!preferences.contains(questEntity.getString("shId"))) {
                    editor.putString(questEntity.getString("shId"), questEntity.toString());
                    String shIds = NiceApplication.instance().getQuestPreferencesQuest().getString(QUEST_IDS, "");
                    shIds = TextUtils.isEmpty(shIds) ? questEntity.getString("shId")
                            : shIds + "," + questEntity.getString("shId");
                    editor.putString(QUEST_IDS, shIds);
                }
            }
            return editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取下载完的问卷
     *
     * @return
     */
    public static List<NicetSheet> getQusetions() {


        SharedPreferences preferences = NiceApplication.instance().getQuestPreferencesQuest();
        String shIdsStr = preferences.getString(QUEST_IDS, "");

        if (TextUtils.isEmpty(shIdsStr)) return null;

        List<NicetSheet> nicetSheetList = new ArrayList<>();
        try {
            if (!shIdsStr.contains(",")) {
                nicetSheetList.add(NicetSheetPaser.paser(new JSONObject(preferences.getString(shIdsStr, null))));
                return nicetSheetList;
            }
            String[] shIds = shIdsStr.split(",");

            for (String shId : shIds) {
                if (preferences.contains(shId)) {
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

    /**
     * 保存答案
     *
     * @param niceValue
     * @return
     */
    public static boolean saveValues(NiceValue niceValue) {
        JSONObject jsonObject = NiceValuePaser.paser2JSONObject(niceValue);
        SharedPreferences.Editor editor = NiceApplication.instance().getValueEditor();
        editor.putString(niceValue.shIdAndGroupId, jsonObject.toString());
        return editor.commit();
    }
    /**
     * 获取string类型的答案
     *
     * @param sqId
     * @return
     */
    public static String getStringValue(long sqId) {
        return NiceApplication.instance().getQuestValuePreferencesQuest().getString(String.valueOf(sqId), null);
    }

    /**
     * 获取set类型的答案
     *
     * @param sqId
     * @return
     */
    public static Set<String> getSetValue(long sqId) {
        return NiceApplication.instance().getQuestValuePreferencesQuest().getStringSet(String.valueOf(sqId), new HashSet());
    }

    /**
     * 获取保存在本地的问卷答案
     * @param nicetSheet
     * @return
     */
    public static List<ValueJsonModel> getNiceValueJSONObject(NicetSheet nicetSheet) {
        List<ValueJsonModel> result = new ArrayList<>();
        String shId = String.valueOf(nicetSheet.shId);
        SharedPreferences preferences = NiceApplication.instance().getQuestValuePreferencesQuest();
        for (NicetSheetQuestionGroup group : nicetSheet.SheetQuestionGroup) {
            String shIdAndqgId = shId + String.valueOf(group.qgId);

            try {
                NiceValue niceValue = NiceValuePaser.paser2NiceValue(
                        new JSONObject(preferences.getString(shIdAndqgId, "")));
                Set<Long> key1 = niceValue.selectedValues.keySet();
                Set<Long> key2 = niceValue.selectedStrutionValues.keySet();
                for (long id : key1) {
                    String key = String.valueOf(id);
                    ValueJsonModel valueJsonModel = new ValueJsonModel();
                    valueJsonModel.sqId = key;
                    valueJsonModel.qaValue = niceValue.selectedValues.get(id);
                    if (valueJsonModel.qaValue.contains(".jpg") || valueJsonModel.qaValue.contains(".jpeg")) {
                        for (int i = 0; i < 3; i++) {
                            valueJsonModel.addFile(valueJsonModel.qaValue);
                        }
                        valueJsonModel.qaValue = new File(valueJsonModel.qaValue).getName();
                    }
                    result.add(valueJsonModel);
                }

                for (long id : key2) {
                    String key = String.valueOf(id);
                    ValueJsonModel valueJsonModel = new ValueJsonModel();
                    valueJsonModel.sqId = key;
                    valueJsonModel.qaValue = niceValue.selectedValues.get(id);
                    result.add(valueJsonModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
        return result;
    }
}
