package com.nice.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import android.app.DownloadManager;
import android.os.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载工具类
 * Created by chen on 2016/1/11.
 */
public class DownloadManagerUtil {


    /**
     * 下载文件
     * @param context
     * @param url
     * @return
     */
    public static long download(Context context, String url){
        DownloadManager.Request request = getDownLoadRequest(context, url);

        DownloadManager downManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return downManager.enqueue(request);

    }

    /**
     * 获得下载request
     * "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"
     * @param context
     * @param url
     * @return
     */
    private static DownloadManager.Request getDownLoadRequest(Context context, String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//        request.setTitle("下载");
//        request.setDescription("今日头条正在下载");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "mydown");
        return request;
    }

    /**
     * 查寻下载文件
     * @param context
     * @param downManager
     * @param status
     * @return
     */
    public static Map<String, String> queryDownTask(Context context, DownloadManager downManager,int status) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(status);
        Cursor cursor= downManager.query(query);
        Map<String, String> map = null;
        while(cursor.moveToNext()){
            String downId= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            //String statuss = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String size= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            map = new HashMap();
            map.put("downid", downId);
            map.put("title", title);
            map.put("address", address);
            map.put("status", sizeTotal+":"+size);
        }
        cursor.close();
        return map;
    }

}
