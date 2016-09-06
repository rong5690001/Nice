package com.nice.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by jiao on 2016/8/3.
 */

public class BitmapWater {
    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    public Bitmap createWatermark(Bitmap target, String mark) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.WHITE);

        // 水印的字体大小
        p.setTextSize(14);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);
        float textWidth = p.measureText(mark);
        // 在左边的中间位置开始添加水印
//        canvas.drawText(mark, (w/4)*3, (h/4)*3, p);
        canvas.drawText(mark, w-textWidth-10, h-20, p);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }
}
