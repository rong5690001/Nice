package com.nice.util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

import java.io.ByteArrayInputStream;
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

    public static String savePhoto(Bitmap bitmap, String sqId) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.i("TestFile",
                    "SD card is not avaiable/writeable right now.");
            return null;
        }
        new DateFormat();
        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        System.out.println("photo_name:" + name);
//        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
        FileOutputStream b = null;
        File file = new File("/sdcard/Image/");
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        String fileName = "/sdcard/Image/" + name;

        try {
            b = new FileOutputStream(fileName);
            System.out.println("vvvvv_qian:"+bitmap.getByteCount());
            float rate = 500*1024f / bitmap.getByteCount()*100;
            rate = rate > 100 ? 100 : rate;
            System.out.println("vvvvv_压缩比:" + rate);
            bitmap.compress(Bitmap.CompressFormat.PNG, (int)rate, b);// 把数据写入文件
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

    /**
     * 获取原图片存储路径
     * @return
     */
    public static String getPhotopath() {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory()+"/mymy/";
//        String imageName = "imageTest.jpg";
        new DateFormat();
        String imageName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }

    /**
     * 根据路径获取图片资源（已缩放）
     * @param url 图片存储路径
     * @return
     */
    public static Bitmap getBitmapFromUrl(String url) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url,options);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
//        int mWidth = bitmap.getWidth();
//        int mHeight = bitmap.getHeight();
        int mWidth = options.outWidth;
        int mHeight = options.outHeight;
        float width = 480f;
        float height = 800f;
        Matrix matrix = new Matrix();
        int be = 1;//be=1表示不缩放
        if (mWidth > mHeight && mWidth > width) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / width);
        } else if (mWidth < mHeight && mHeight > height) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / height);
        }
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(url, options);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }


    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
