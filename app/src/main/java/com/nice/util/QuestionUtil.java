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
import java.util.Map;
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
                    editor.commit();
                }
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存问卷
     *
     * @param jsonObject
     * @return
     */
    public static boolean saveQuestion2(JSONObject jsonObject) {
        SharedPreferences.Editor editor = NiceApplication.instance().getQuestEditor();
        try {
            editor.putString(jsonObject.getString("shId"), jsonObject.toString());
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
     *
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
                Set<Long> key3 = niceValue.mutiSelectedValues.keySet();
                for (long id : key1) {
                    String key = String.valueOf(id);
                    if (!niceValue.selectedValues.get(id).equals("fdjsalkjfklsajklfdjsalkjfklsajdklfjdskaljfkdlsjaklfjdsakuiorewuqoiruewioqurewioqurioewuqioreuwqiourioewquioreuwioqurioewquoi")) {
                        ValueJsonModel valueJsonModel = new ValueJsonModel();
                        if (key.length() > 15) {
                            key = key.substring(0, key.length() - 1);
                        }
                        valueJsonModel.sqId = key;
                        //判断是否选择说明题
                        if (niceValue.selectedValues.get(id).contains("陈华榕陈华榕陈华榕陈华榕陈华榕")) {
                            String[] v = niceValue.selectedValues.get(id).split("陈华榕陈华榕陈华榕陈华榕陈华榕");
                            valueJsonModel.qaValue = v[0];
                            if (v.length > 1) {
                                valueJsonModel.qoMemo = v[1];
                            }
                        } else {
                            valueJsonModel.qaValue = niceValue.selectedValues.get(id);
                        }
                        valueJsonModel.files = new ArrayList<>();
                        if (valueJsonModel.qaValue.contains(".jpg") || valueJsonModel.qaValue.contains(".jpeg")) {
                            valueJsonModel.addFile(valueJsonModel.qaValue);
                            if (niceValue.selectedValues.containsKey(id - 2)) {
                                valueJsonModel.addFile(niceValue.selectedValues.get(id - 2));
                                niceValue.selectedValues.put(id - 2, "fdjsalkjfklsajklfdjsalkjfklsajdklfjdskaljfkdlsjaklfjdsakuiorewuqoiruewioqurewioqurioewuqioreuwqiourioewquioreuwioqurioewquoi");
                            }
                            if (niceValue.selectedValues.containsKey(id - 1)) {
                                valueJsonModel.addFile(niceValue.selectedValues.get(id - 1));
                                niceValue.selectedValues.put(id - 1, "fdjsalkjfklsajklfdjsalkjfklsajdklfjdskaljfkdlsjaklfjdsakuiorewuqoiruewioqurewioqurioewuqioreuwqiourioewquioreuwioqurioewquoi");
                            }
                            if (niceValue.selectedValues.containsKey(id + 1)) {
                                valueJsonModel.addFile(niceValue.selectedValues.get(id + 1));
                                niceValue.selectedValues.put(id + 1, "fdjsalkjfklsajklfdjsalkjfklsajdklfjdskaljfkdlsjaklfjdsakuiorewuqoiruewioqurewioqurioewuqioreuwqiourioewquioreuwioqurioewquoi");
                            }
                            if (niceValue.selectedValues.containsKey(id + 2)) {
                                valueJsonModel.addFile(niceValue.selectedValues.get(id + 2));
                                niceValue.selectedValues.put(id + 2, "fdjsalkjfklsajklfdjsalkjfklsajdklfjdskaljfkdlsjaklfjdsakuiorewuqoiruewioqurewioqurioewuqioreuwqiourioewquioreuwioqurioewquoi");
                            }

                            valueJsonModel.qaValue = new File(valueJsonModel.qaValue).getName();
                        }
                        result.add(valueJsonModel);
                    }
                }

                for (long id : key2) {
                    String key = String.valueOf(id);
                    ValueJsonModel valueJsonModel = new ValueJsonModel();
                    valueJsonModel.sqId = key;
                    valueJsonModel.qaValue = niceValue.selectedValues.get(id);
                    result.add(valueJsonModel);
                }

                for (long id : key3) {
                    Map<String, String> mutiMap = niceValue.mutiSelectedValues.get(id);
                    Set<String> key3_child = mutiMap.keySet();
                    for (String id_c : key3_child) {
                        String key = String.valueOf(id_c);
                        ValueJsonModel valueJsonModel = new ValueJsonModel();
                        valueJsonModel.sqId = String.valueOf(id);
                        valueJsonModel.qaValue = mutiMap.get(id_c);
                        result.add(valueJsonModel);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
        return result;
    }

    /**
     * 删除问卷
     *
     * @param sqId
     * @return
     */
    public static boolean delQuestion(String sqId) {
        SharedPreferences preferences = NiceApplication.instance().getQuestPreferencesQuest();
        String[] ids = preferences.getString(QUEST_IDS, "").split(",");
        String result = "";
        for (String id : ids) {
            if (!id.equals(sqId)) {
                if (TextUtils.isEmpty(result)) {
                    result = id;
                } else {
                    result = result + "," + id;
                }
            }
        }
        SharedPreferences.Editor editor = NiceApplication.instance().getQuestEditor();
        editor.putString(QUEST_IDS, result);
        editor.remove(sqId);
        return editor.commit();
    }

    /**
     * 获取完成度
     *
     * @param nicetSheet
     * @return
     */
    public static int getCompleteness(NicetSheet nicetSheet) {
        if (null == nicetSheet) {
            return 0;
        }
        if (null == nicetSheet.SheetQuestionGroup) return 0;
        float result = 0f;
        SharedPreferences preferences = NiceApplication.instance().getPreferencesCompleteness();
        for (NicetSheetQuestionGroup group : nicetSheet.SheetQuestionGroup) {
            result += preferences.getFloat(nicetSheet.shId + "" + group.qgId, 0f);
        }
        return (int) (result * 100) > 100 ? 100 : (int) (result * 100);
    }

    /**
     * 获取问卷总的问题数量
     *
     * @param nicetSheet
     * @return
     */
    private static float getQuestionCount(NicetSheet nicetSheet) {
        int count = 0;
        for (NicetSheetQuestionGroup group : nicetSheet.SheetQuestionGroup) {
            count += group.SheetQuestion.size();
        }
        return (float) count;
    }

    /**
     * 保存完成度
     *
     * @param niceValue
     * @param nicetSheet
     * @return
     */
    public static boolean saveCompleteness(NiceValue niceValue, NicetSheet nicetSheet) {
        if (null == niceValue) return false;
        SharedPreferences.Editor editor = NiceApplication.instance().getCompletenessEditor();
        float doNum = 0;
        if (null != niceValue.selectedValues) {
            doNum += niceValue.selectedValues.keySet().size();
        }
        if (null != niceValue.mutiSelectedValues) {
            doNum += niceValue.mutiSelectedValues.keySet().size();
        }
        editor.putFloat(niceValue.shIdAndGroupId, doNum / getQuestionCount(nicetSheet));
        return editor.commit();
    }

    /**
     * 删除完成度
     *
     * @param nicetSheet
     * @return
     */
    public static boolean delCompleteness(NicetSheet nicetSheet) {
        if (null == nicetSheet) return false;
        SharedPreferences.Editor editor = NiceApplication.instance().getCompletenessEditor();
        for (NicetSheetQuestionGroup group : nicetSheet.SheetQuestionGroup) {
            editor.remove(String.valueOf(group.qgId));
        }
        return editor.commit();
    }

}
