package com.nice.util;

import com.nice.NiceApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chen on 2016/1/27.
 */
public class FileUtil {



    public static boolean saveQuestions(JSONObject jsonObject){

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

}
