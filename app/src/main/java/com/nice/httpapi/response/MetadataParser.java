package com.nice.httpapi.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by i068020 on 2/8/15.
 */
public class MetadataParser {
    public static boolean hasError(JSONObject response) {
        try {
            return response.getJSONObject("status").has("1");
        } catch (JSONException e) {
            return false;
        }
    }

    public static String getError(String response) {
        try {
            return getError(new JSONObject(response));
        } catch (JSONException e) {
        }
        return "-22";
    }

    public static String getError(JSONObject response) {
        try {
            if (response.has("errMsg")) {
                return response.getString("errMsg");
            } else {
                return "-22";
            }
        } catch (JSONException e) {
            return "-22";
        }
    }

//    public static PagingMetadata parsePaging(JSONObject response) {
//        try {
//            String metadataString = response.getJSONObject("metadata").toString();
//            Type metadataType = new TypeToken<PagingMetadata>() {
//            }.getType();
//            Gson gson = QSGsonFactory.create();
//            return gson.fromJson(metadataString, metadataType);
//        } catch (JSONException e) {
//            return null;
//        }
//    }
//
//    public static String getNumTotal(JSONObject response) {
//        PagingMetadata pagingMetadata = parsePaging(response);
//        if (pagingMetadata == null) {
//            return "0";
//        } else {
//            return pagingMetadata.numTotal + "";
//        }
//    }
//
//    public static GregorianCalendar getRefreshTime(JSONObject response) {
//        try {
//            String refreshTime = ((JSONObject) response.get("metadata")).get("refreshTime").toString();
//            return TimeUtil.parseUTC(refreshTime);
//        } catch (Exception e) {
//            GregorianCalendar calendar = new GregorianCalendar();
//            calendar.setTime(new Date());
//            return calendar;
//        }
//    }
}
