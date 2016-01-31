package com.nice.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by chen on 2016/1/31.
 */
public class BitmapUtil {

    public static Bitmap file2Bitmap(String fileName){
        return BitmapFactory.decodeFile(fileName);
    }

    public static Bitmap file2BitmapWithSize(String fileName){
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return Bitmap.createScaledBitmap(bitmap, 150, 150, true);
    }
}
