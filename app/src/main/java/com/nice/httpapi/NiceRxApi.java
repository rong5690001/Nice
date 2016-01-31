package com.nice.httpapi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.nice.NiceApplication;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.httpapi.request.QSJsonObjectRequest;
import com.nice.httpapi.request.RequestQueueManager;
import com.nice.httpapi.request.RxRequest;
import com.nice.httpapi.response.dataparser.NiceUserPaser;
import com.nice.httpapi.response.dataparser.NicetSheetPaser;
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
import java.util.Objects;

import rx.Observable;
import rx.functions.Func1;

public class NiceRxApi {

    private static final String HOST_NAME = "http://180.76.131.23/admin/logic";
    private static final String LOGIN_URL = HOST_NAME + "/IOSAndroidDataService.ashx";
//            +"clientTimeStamp=1450015351071&method=uUserInfo&mode=2001&clientType=ios&version=1.0" +
//            "&encryptCode=0284c86a3ee1f5273ebc887797032948&transfer=121212" +
//            "&requestJson={uiCellPhone:13000000000,uiPassword:098f6bcd4621d373cade4e832627b4f6}";

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
//        QSJsonObjectRequest jsonObjectRequest = new QSJsonObjectRequest(LOGIN_URL, new JSONObject(params), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                System.out.println("response:" + response);
//            }
//        });


        return RxRequest.createJsonRequest(Request.Method.POST, LOGIN_URL, new JSONObject(params))
                .map(new Func1<JSONObject, NiceUser>() {
                    @Override
                    public NiceUser call(JSONObject jsonObject) {
                        System.out.println("jsonObject" + jsonObject);
//                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
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
//                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
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
//                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
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
//                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
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
//                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
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
//                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
                        return jsonObject;
                    }
                });
    }

//    public static Observable<List<FeedingAggregation>> queryFeedingaggregationLatest(){
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getFeedingaggregationLatest(), null)
//                .map(new Func1<JSONObject, List<FeedingAggregation>>() {
//                    @Override
//                    public List<FeedingAggregation> call(JSONObject jsonObject) {
//                    List<FeedingAggregation> feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
//                    Collections.sort(feedingAggregations, new Comparator<FeedingAggregation>() {
//                        @Override
//                        public int compare(FeedingAggregation lhs, FeedingAggregation rhs) {
//                            return Integer.parseInt(rhs.key) - Integer.parseInt(lhs.key);
//                        }
//                    });
//                    return feedingAggregations;
//                    }
//                }).flatMap(new Func1<List<FeedingAggregation>, Observable<FeedingAggregation>>() {
//                    @Override
//                    public Observable<FeedingAggregation> call(List<FeedingAggregation> feedingAggregations) {
//                        return Observable.from(feedingAggregations);
//                    }
//                }).filter(new Func1<FeedingAggregation, Boolean>() {
//                    @Override
//                    public Boolean call(FeedingAggregation feedingAggregation) {
//                        return feedingAggregation.topShows.size() > 0;
//                    }
//                }).toList();
//    }
//
//    public static Observable<List<MongoShow>> feedingTime(int pageNo, int pageSize, String from, String to){
//        Map<String, Object> reqData = new HashMap<>();
//        reqData.put("from", from);
//        reqData.put("to", to);
//
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getFeedingTimeApi(pageNo, pageSize, from, to), null)
//                .map(new Func1<JSONObject, List<MongoShow>>() {
//                    @Override
//                    public List<MongoShow> call(JSONObject jsonObject) {
//                        return ShowParser.parseQuery(jsonObject);
//                    }
//                });
//    }
//
//    public static Observable<List<MongoTrade>> tradeOwn(int pageNo, int pageSize){
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getTradeOwn(pageNo, pageSize), null)
//            .map(new Func1<JSONObject, List<MongoTrade>>() {
//                @Override
//                public List<MongoTrade> call(JSONObject jsonObject) {
//                    return TradeParser.parseQuery(jsonObject);
//                }
//            });
//    }
//
//    public static Observable<RemixByItem> remixByItem(String itemRef){
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getRemixByItem(itemRef), null)
//                .map(new Func1<JSONObject, RemixByItem>() {
//                    @Override
//                    public RemixByItem call(JSONObject jsonObject) {
//                        return RemixByItemParser.parse(jsonObject);
//                    }
//                });
//    }
//
//    public static Observable<RemixByModel> remixByModel(String modelRef){
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getRemixByModel(modelRef),null)
//            .map(new Func1<JSONObject, RemixByModel>() {
//                @Override
//                public RemixByModel call(JSONObject jsonObject) {
//                    return RemixByModelParser.parse(jsonObject);
//                }
//            });
//    }
//
//    public static Observable<List<MongoPeople>> queryBuyers(String itemRef){
//        Map<String, Object> reqData = new HashMap<>();
//        reqData.put("itemRef", itemRef);
//        return RxRequest.createJsonRequest(Method.POST, QSAppWebAPI.getQueryBuyers(),new JSONObject(reqData))
//                .map(new Func1<JSONObject, List<MongoPeople>>() {
//                    @Override
//                    public List<MongoPeople> call(JSONObject jsonObject) {
//                        return PeopleParser.parseQuery(jsonObject);
//                    }
//                });
//    }
//
//    public static Observable<List<MongoPeople>> queryPeople(String ... ids){
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getPeopleQueryApi(ids), null)
//                .map(new Func1<JSONObject, List<MongoPeople>>() {
//                    @Override
//                    public List<MongoPeople> call(JSONObject jsonObject) {
//                        return PeopleParser.parseQuery(jsonObject);
//                    }
//                });
//    }
//
//    public static Observable<ArrayList<MongoBonus>> getOwnBonus(){
//        return RxRequest.createJsonRequest(Method.GET,QSAppWebAPI.getBonusOwn(),null)
//                .map(new Func1<JSONObject, ArrayList<MongoBonus>>() {
//                    @Override
//                    public ArrayList<MongoBonus> call(JSONObject jsonObject) {
//                        return BonusParser.parseQuery(jsonObject);
//                    }
//                });
//    }
//
//    public static Observable<ArrayList<MongoBonus>> queryBonus(String ... ids){
//        return RxRequest.createJsonRequest(Method.GET, QSAppWebAPI.getQueryBonus(ids),null)
//                .map(new Func1<JSONObject, ArrayList<MongoBonus>>() {
//                    @Override
//                    public ArrayList<MongoBonus> call(JSONObject jsonObject) {
//                        return BonusParser.parseQuery(jsonObject);
//                    }
//                });
//    }


}
