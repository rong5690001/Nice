package com.nice.util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.nice.NiceApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by chen on 2016/1/27.
 */
public class FileUtil {


    public static boolean saveQuestions(JSONObject jsonObject) {

        File file = null;

        System.out.println(file.getParent());

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            for (int i = 0; i < jsonArray.length(); i++) {
                file = new File(NiceApplication.instance().getFilesDir() + "/quest"
                        , jsonArray.getJSONObject(i).getString("shName"));

                inputStream = new FileInputStream(file);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String savePhoto(Intent data, String sqId) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.i("TestFile",
                    "SD card is not avaiable/writeable right now.");
            return null;
        }
        new DateFormat();
        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        System.out.println("photo_name:" + name);
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

        FileOutputStream b = null;
        File file = new File("/sdcard/Image/");
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        String fileName = "/sdcard/Image/" + name;

        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            if (!TextUtils.isEmpty(sqId)) {
                SharedPreferences.Editor editor = NiceApplication.instance().getQuestValuePreferencesQuest().edit();
                editor.putString(sqId, fileName);
                editor.commit();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return name;

    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
