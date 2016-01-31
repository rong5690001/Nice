package com.nice.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传的答案实体
 * Created by chen on 2016/1/31.
 */
public class ValueJsonModel implements Serializable {


    public String sqId;
    public String qaValue;
    public List<Files> files;

    class Files {
        public String filename;
        public String file;
    }


    public void addFile(String fileName){
        if(null == files){
            files = new ArrayList<>();
        }
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);

        Files file1 = new Files();
        File file2 = new File(fileName);
        file1.filename = file2.getName();
        file1.file = Base64.encode(bitmap.getNinePatchChunk(), 0).toString();
        System.out.println("fileBase64:" + file1.file);
    }

}
