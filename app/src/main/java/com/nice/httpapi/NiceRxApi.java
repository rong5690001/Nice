package com.nice.httpapi;

import com.android.volley.Response;
import com.nice.httpapi.request.QSJsonObjectRequest;
import com.nice.httpapi.request.RequestQueueManager;
import com.nice.model.NiceUser;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import rx.Observable;

public class NiceRxApi {

    private static final String HOST_NAME = "http://180.76.131.23/admin/logic";
    private static final String LOGIN_URL = HOST_NAME + "/IOSAndroidDataService.ashx";
//            +"clientTimeStamp=1450015351071&method=uUserInfo&mode=2001&clientType=ios&version=1.0" +
//            "&encryptCode=0284c86a3ee1f5273ebc887797032948&transfer=121212" +
//            "&requestJson={uiCellPhone:13000000000,uiPassword:098f6bcd4621d373cade4e832627b4f6}";

    public static Observable<NiceUser> login(){

        Map params = new LinkedHashMap();
        params.put("encryptCode","0284c86a3ee1f5273ebc887797032948");
        params.put("transfer","121212");
        params.put("clientTimeStamp","1450015351071");
        params.put("method","uUserInfo");
        params.put("mode","2001");
        params.put("clientType","android");
        params.put("version","1.0");

        Map requestJson = new LinkedHashMap();
        requestJson.put("uiCellPhone", "13000000000");
        requestJson.put("uiPassword", "098f6bcd4621d373cade4e832627b4f6");
        params.put("requestJson",requestJson);
        System.out.println(new JSONObject(params));
        QSJsonObjectRequest jsonObjectRequest = new QSJsonObjectRequest(LOGIN_URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response:" + response);
            }
        });


        RequestQueueManager.INSTANCE.getQueue().add(jsonObjectRequest);
        return null;
//        return RxRequest.createJsonRequest(Method.GET, LOGIN_URL, null)
//                .map(new Func1<JSONObject, NiceUser>() {
//                    @Override
//                    public NiceUser call(JSONObject jsonObject) {
//                        System.out.println("jsonObject" + jsonObject);
////                     feedingAggregations = FeedingAggregationParser.parseQuery(jsonObject);
//                        return NiceUserPaser.paserNiceUser(jsonObject).get(0);
//                    }
//                });
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
