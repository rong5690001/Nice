package com.nice.httpapi;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nice.NiceApplication;
import com.nice.R;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.httpapi.request.RxRequest;
import com.nice.httpapi.response.dataparser.NiceUserPaser;
import com.nice.model.NiceUser;
import com.nice.model.NicetSheet;
import com.nice.model.SignInModel;
import com.nice.model.ValueJsonModel;
import com.nice.ui.IncompleteQuestionActivity;
import com.nice.ui.LoadingDialog;
import com.nice.ui.LoadingDialog_hq;
import com.nice.ui.QuestionContextActivity;
import com.nice.util.MD5;
import com.nice.util.QuestionUtil;
import com.nice.widget.NiceButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Func1;

public class NiceRxApi {

//    private static final String HOST_NAME = "http://139.219.141.225:8888/admin/logic";
//    private static final String HOST_NAME = "http://180.76.131.23/admin/logic";
    private static final String HOST_NAME = "https://onsite.huaxiadnb.cn/admin/logic";
    private static final String LOGIN_URL = HOST_NAME + "/IOSAndroidDataService.ashx";
    private static LoadingDialog_hq dialogs;
    private static QuestionContextActivity niceContent;
    private static Map getParams(String method,String mode) {
        Map params = new LinkedHashMap();
        params.put("encryptCode", MD5.MD5Encode(method+mode+"1.0"));
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

        Map params = getParams("uUserInfo","2001");
        params.put("method", "uUserInfo");
        params.put("mode", "2001");


//        params.put("uiCode", uiCode);
//        params.put("uiPassword", uiPassword);
//        Map requestJson = new LinkedHashMap();
        Map requestJson = new LinkedHashMap();
        requestJson.put("uiCode", uiCode);
        requestJson.put("uiPassword", uiPassword);
        params.put("requestJson", requestJson);
        System.out.println("jiaojiabinggg"+requestJson);

        System.out.println("jiaojiabin"+QSGsonFactory.create().toJson(params));
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(QSGsonFactory.create().toJson(params));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, jsonObject)
                .map(new Func1<JSONObject, NiceUser>() {
                    @Override
                    public NiceUser call(JSONObject jsonObject) {
                        System.out.println("jsonObject" + jsonObject.toString());
                        System.out.println("登陆" +NiceUserPaser.paserNiceUser(jsonObject));
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

        Map params = getParams("uSheet","1001");

        params.put("method", "uSheet");
        params.put("mode", "1001");

        List<Map> shIdList = new ArrayList<>();

        for (String shid : shIds) {
            Map shIdMap = new HashMap();
            shIdMap.put("shId", shid);
            shIdList.add(shIdMap);
        }



        params.put("requestJson", shIdList);

        System.out.println(new JSONObject(params));
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(QSGsonFactory.create().toJson(params));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, jsonObject)
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
        Map params = getParams("uUserInfo","1002");
        params.put("method", "uUserInfo");
        params.put("mode", "1002");
        params.put("uiId", uiId);
//        Map requestJson = new HashMap();
//        requestJson.put("uiId", uiId);
//        params.put("requestJson", requestJson);

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
        Map params = getParams("uUserInfo","1003");
        params.put("method", "uUserInfo");
        params.put("mode", "1003");
        params.put("uiId", uiId);
        Map requestJson = new HashMap();
        requestJson.put("uiId", uiId);
        params.put("requestJson", requestJson);

        System.out.println("已上传问卷信息:" + new JSONObject(params));

        JSONObject jsonObjectt = null;
        try {
            jsonObjectt = new JSONObject(QSGsonFactory.create().toJson(params));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, jsonObjectt)
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

        Map params = getParams("uUserInfo","1002");

        params.put("method", "uUserInfo");
        params.put("mode", "1002");

//        Map<String, String> requestJson = new HashMap<>();
//        requestJson.put("uiId", String.valueOf(NiceApplication.user.uiId));
//        params.put("requestJson", requestJson);
        params.put("uiId", String.valueOf(NiceApplication.user.uiId));
        System.out.println("params:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        return jsonObject;
                    }
                });
    }
    @Bind(R.id.submit_btn)
    NiceButton submitBtn;
    static int i=0;
    ProgressBar progressBar=null;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    submitBtn.setClickable(false);
                    i+=((Math.random()+1)*10);
                    if(i>=100){
                        i=100;
                    }
                    progressBar.setProgress(i);
                    if(i!=100){
                        handler.sendEmptyMessageDelayed(0x123,500);
                        submitBtn.setText("正在获取本地数据"+i+"%");
                    }else if(i==100){
                        handler.sendEmptyMessageDelayed(0x321,500);
//                        startActivity(new Intent(NewQuestActivity.this, IncompleteQuestionActivity.class));
                        dialogs.dismiss();
                    }
                    break;
//                case 0x321:
//                    submitBtn.setText(打开);
//                    submitBtn.setClickable(true);
//                    submitBtn.setBackgroundResource(R.drawable.aa_button_after);
//                    handler.sendEmptyMessageDelayed(0x110,1000);
//                    break;
                case 0x110:
                    progressBar.setProgress(0);
                    submitBtn.setBackgroundResource(R.drawable.btn_selector);
                default:
                    break;
            }
        };
    };

    /**
     * 提交问卷
     *
     * @return
     */
    public static Observable<JSONObject> commitQuestion(NicetSheet nicetSheet) {
        Gson gson = QSGsonFactory.create();
        i = 0;
        dialogs = new LoadingDialog_hq(niceContent);
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.show();
        String requestStr = gson.toJson(QuestionUtil.getNiceValueJSONObject(nicetSheet));

        Type type = new TypeToken<List<ValueJsonModel>>(){}.getType();
//        List<ValueJsonModel> requestJson = gson.to(requestStr, type);

        Map params = getParams("uSheet","1002");
        params.put("method", "uSheet");
        params.put("mode", "1002");
        params.put("uiId", String.valueOf(NiceApplication.user.uiId));

        try {
            params.put("requestJson", new JSONArray(requestStr));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("上传问卷:" + new JSONObject(params));
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("提交_返回值:" + jsonObject);
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

        Map params = getParams("uUserInfo","1005");

        params.put("method", "uUserInfo");
        params.put("mode", "1005");
        params.put("uiId", String.valueOf(NiceApplication.user.uiId));
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("shId", String.valueOf(shId));
        params.put("requestJson", requestJson);
        System.out.println("问卷ID查询订单信息:" + new JSONObject(params));

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(QSGsonFactory.create().toJson(params));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL,jsonObject)
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("问卷ID查询订单信息_jsonObject" + jsonObject);
                        return jsonObject;
                    }
                });

    }

    /**
     * 订单ID
     * @return
     */
    public static Observable<JSONObject> backOrder(long id, String oiMemo) {

        String uiId = String.valueOf(NiceApplication.user.uiId);
        Map params = getParams("uUserInfo","1004");
        params.put("method", "uUserInfo");
        params.put("mode", "1004");
        params.put("uiId", uiId);
        Map requestJson = new HashMap();
        requestJson.put("oiId", String.valueOf(id));
        requestJson.put("oiMemo", oiMemo);
        params.put("requestJson", requestJson);

        System.out.println("退回:" + new JSONObject(params));

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(QSGsonFactory.create().toJson(params));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, jsonObject)
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("退回_返回值:" + jsonObject);
                        return jsonObject;
                    }
                });
    }

    /**
     * 签到
     * @param signInModel
     * @return
     */
    public static Observable<JSONObject> signIn(SignInModel signInModel){
        Gson gson = QSGsonFactory.create();
        String requestStr = gson.toJson(signInModel);
        String uiId = String.valueOf(NiceApplication.user.uiId);
        Map params = getParams("uSheet","2002");
        params.put("method", "uSheet");
        params.put("mode", "2002");
        params.put("uiId", uiId);

        try {
            params.put("requestJson", new JSONObject(requestStr));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("签到:" + new JSONObject(params));

//        JSONObject jsonObjectr = null;
//        try {
//            jsonObjectr = new JSONObject(QSGsonFactory.create().toJson(params));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject call(JSONObject jsonObject) {
                        System.out.println("签到_返回值:" + jsonObject);
                        return jsonObject;
                    }
                });
    }

}
