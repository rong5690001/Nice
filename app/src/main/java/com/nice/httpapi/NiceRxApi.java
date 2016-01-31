package com.nice.httpapi;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.nice.NiceApplication;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.httpapi.request.RxRequest;
import com.nice.httpapi.response.dataparser.NiceUserPaser;
import com.nice.model.NiceUser;
import com.nice.model.NicetSheet;
import com.nice.model.ValueJsonModel;
import com.nice.util.QuestionUtil;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.functions.Func1;

public class NiceRxApi {

    private static final String HOST_NAME = "http://180.76.131.23/admin/logic";
    private static final String LOGIN_URL = HOST_NAME + "/IOSAndroidDataService.ashx";

    private static Map getParams() {
        Map params = new LinkedHashMap();
        params.put("encryptCode", "0284c86a3ee1f5273ebc887797032948");
        params.put("transfer", "121212");
        params.put("clientTimeStamp", String.valueOf(System.currentTimeMillis()));
        params.put("clientType", "android");
        params.put("version", "1.0");
        return params;
    }

    /**
     * 登录
     *
     * @param uiCode
     * @param uiPassword
     * @return
     */
    public static Observable<NiceUser> login(String uiCode, String uiPassword) {

        Map params = getParams();
        params.put("method", "uUserInfo");
        params.put("mode", "2001");

        Map requestJson = new LinkedHashMap();
        requestJson.put("uiCode", uiCode);
        requestJson.put("uiPassword", uiPassword);
        params.put("requestJson", requestJson);
        System.out.println(new JSONObject(params));

        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, NiceUser>() {
                    @Override
                    public NiceUser call(JSONObject jsonObject) {
                        System.out.println("jsonObject" + jsonObject);
                        return NiceUserPaser.paserNiceUser(jsonObject).get(0);
                    }
                });
    }

    /**
     * 下载问卷
     *
     * @param shIds
     * @return
     */
    public static Observable<JSONObject> Download(List<String> shIds) {

        Map params = getParams();

        params.put("method", "uSheet");
        params.put("mode", "1001");

        Map shIdMap = new HashMap();

        for (String shid : shIds) {
            shIdMap.put("shId", shid);
        }

        List<Map> shIdList = new ArrayList<>();
        shIdList.add(shIdMap);
        params.put("requestJson", shIdList);

        System.out.println(new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("jsonObject" + jsonObject);
                        return jsonObject;
                    }
                });

    }

    /**
     * 获取新问卷信息
     * @return
     */
    public static Observable<JSONObject> getNewQuestionInfo() {

        String uiId = String.valueOf(NiceApplication.user.uiId);
        Map params = getParams();
        params.put("method", "uUserInfo");
        params.put("mode", "1002");
        params.put("uiId", uiId);
        Map requestJson = new HashMap();
        requestJson.put("uiId", uiId);
        params.put("requestJson", requestJson);

        System.out.println("新问卷信息:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("新问卷信息_返回值:" + jsonObject);
                        return jsonObject;
                    }
                });
    }

    /**
     * 已上传问卷信息
     * @return
     */
    public static Observable<JSONObject> getUploadedQuestion() {

        String uiId = String.valueOf(NiceApplication.user.uiId);
        Map params = getParams();
        params.put("method", "uUserInfo");
        params.put("mode", "1003");
        params.put("uiId", uiId);
        Map requestJson = new HashMap();
        requestJson.put("uiId", uiId);
        params.put("requestJson", requestJson);

        System.out.println("已上传问卷信息:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("已上传问卷信息_返回值:" + jsonObject);
                        return jsonObject;
                    }
                });

    }

    /**
     * 获取新问卷
     *
     * @return
     */
    public static Observable<JSONObject> getNewQuestion() {

        Map params = getParams();

        params.put("method", "uUserInfo");
        params.put("mode", "1002");

        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("uiId", String.valueOf(NiceApplication.user.uiId));
        params.put("requestJson", requestJson);
        System.out.println("params:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("新问卷_jsonObject" + jsonObject);
                        return jsonObject;
                    }
                });
    }

    /**
     * 提交问卷
     *
     * @return
     */
    public static Observable<JSONObject> commitQuestion(NicetSheet nicetSheet) {

        List<ValueJsonModel> requestJson = QuestionUtil.getNiceValueJSONObject(nicetSheet);

        Map params = getParams();
        params.put("method", "uSheet");
        params.put("mode", "1002");
        params.put("uiId", String.valueOf(NiceApplication.user.uiId));
        Gson gson = QSGsonFactory.create();
        params.put("requestJson", requestJson);

        System.out.println("上传问卷:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("上传结果" + jsonObject);
                        return jsonObject;
                    }
                });
    }

    /**
     * 根据问卷ID查询订单信息
     * @param shId
     * @return
     */
    public static Observable<JSONObject> getSheetInfo(long shId) {

        Map params = getParams();

        params.put("method", "uUserInfo");
        params.put("mode", "1005");
        params.put("uiId", String.valueOf(NiceApplication.user.uiId));
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("shId", String.valueOf(shId));
        params.put("requestJson", requestJson);
        System.out.println("问卷ID查询订单信息:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("问卷ID查询订单信息_jsonObject" + jsonObject);
                        return jsonObject;
                    }
                });

    }

}
