package com.nice.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.nice.util.FileUtil;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传的答案实体
 * Created by chen on 2016/1/31.
 */
public class ValueJsonModel implements Serializable {


    public String sqId;
    public String qaValue;
    public String qoMemo;
    public List<Files> files = new ArrayList<>();

    class Files {
        public String filename;
        public String file;
    }


    public void addFile(String fileName){
        if(null == files){
            files = new ArrayList<>();
        }
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Image/" + fileName);

        if(null == bitmap){
            return;
        }

        Files file1 = new Files();
        File file2 = new File("/sdcard/Image/" + fileName);
        file1.filename = file2.getName();
        try {
            file1.file = new String(Base64.encode(FileUtil.Bitmap2Bytes(bitmap), 0), "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        files.add(file1);
        System.out.println("fileBase64:" + file1.file);
    }

}
