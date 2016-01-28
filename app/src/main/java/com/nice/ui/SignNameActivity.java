package com.nice.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignNameActivity extends AppCompatActivity implements OnClickListener {

    @Bind(R.id.back_icon)
    NiceImageView backIcon;
    @Bind(R.id.back_layout)
    RelativeLayout backLayout;
    @Bind(R.id.title)
    NiceTextView title;
    @Bind(R.id.right_icon)
    NiceImageView rightIcon;
    @Bind(R.id.right_text)
    NiceTextView rightText;
    @Bind(R.id.right_btn_layout)
    RelativeLayout rightBtnLayout;
    @Bind(R.id.surfVDraw)
    SurfaceView surfVDraw;
    @Bind(R.id.resigname_btn)
    LinearLayout resignameBtn;
    @Bind(R.id.sign_commit_btn)
    LinearLayout signCommitBtn;
    @Bind(R.id.sign_bottom_tab)
    LinearLayout signBottomTab;

    private SurfaceHolder surHolder = null;
    private Paint myPaint = null;
    private Bitmap mBitmap = null; //create bitmap for to save the pixels
    private Canvas mCanvas = null;
    private PointF mLeftSelectPoint;
    private Path mPath;
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_name);
        surfVDraw.setZOrderOnTop(true);//设置画布  背景透明
        surfVDraw.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        ButterKnife.bind(this);
        surHolder = surfVDraw.getHolder();

        mPaint = new Paint();
        //set parameters of paint.
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //set the width of stroke.
        mPaint.setStrokeWidth(12);
        //setting my paint to draw
        myPaint = new Paint();
        myPaint.setColor(getResources().getColor(R.color.blue));
        myPaint.setStrokeWidth(1);
        mLeftSelectPoint = new PointF(0, 0);
        mPath = new Path();

        surHolder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                System.out.println("ffffffffffffff");
                if (mBitmap == null) {
                    mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                    mCanvas = new Canvas(mBitmap);

//                    BitmapDrawable bitmapDraw = (BitmapDrawable) (getResources().getDrawable(R.mipmap.zhangsan));
//                    Bitmap bitmap = bitmapDraw.getBitmap();
//
//                    if (bitmap != null) {
//                        mCanvas.drawBitmap(bitmap, 0, 0, myPaint);
//                    }

                } else {

                }

                Log.i("surfaceChange", "surface");
            }

            public void surfaceCreated(SurfaceHolder holder) {
                if (mBitmap == null) {
                    mBitmap = Bitmap.createBitmap(surfVDraw.getWidth(), surfVDraw.getHeight(), Config.ARGB_8888);
                    mCanvas = new Canvas(mBitmap);

//                    BitmapDrawable bitmapDraw = (BitmapDrawable) (getResources().getDrawable(R.mipmap.zhangsan));
//                    Bitmap bitmap = bitmapDraw.getBitmap();
//
//                    if (bitmap != null) {
//                        mCanvas.drawBitmap(bitmap, 0, 0, myPaint);
//                    }

                }
                // 首先定义一个paint
                Paint paint = new Paint();

                // 绘制矩形区域-实心矩形
                // 设置颜色
                paint.setColor(Color.WHITE);
                // 设置样式-填充
                paint.setStyle(Paint.Style.FILL);
                // 绘制一个矩形
                mCanvas.drawRect(new Rect(0, 0, surfVDraw.getWidth(), surfVDraw.getHeight()), paint);
//                surfVDraw.setBackgroundColor(getResources().getColor(R.color.transparent));
//                surHolder.unlockCanvasAndPost(mCanvas);
            }

            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        surfVDraw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touch_start(x, y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touch_move(x, y);
                        break;
                    case MotionEvent.ACTION_UP:
                        touch_up();
                        break;
                }
                return true;
            }
        });
        initLayout();
    }

    private void initLayout() {
        title.setText("签到");
        rightBtnLayout.setVisibility(View.GONE);
    }

    public void myDraw() {
        Canvas canvas = surHolder.lockCanvas();
        canvas.drawBitmap(mBitmap, 0, 0, null);
        surHolder.unlockCanvasAndPost(canvas);
    }

    private byte[] saveBuffer() {
        byte[] buffer = new byte[mBitmap.getRowBytes() * mBitmap.getHeight()];
        Buffer byteBuffer = ByteBuffer.wrap(buffer);
        mBitmap.copyPixelsToBuffer(byteBuffer);
        return buffer;
    }

    public void saveBitmap(Bitmap bitmap) throws FileNotFoundException {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath()
                    .toString()
                    + "/external_sdcard";
            FileOutputStream fos = new FileOutputStream(sdcard + "lin.jpeg");
            bitmap.compress(CompressFormat.JPEG, 100, fos);

        }


    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
        mCanvas.drawPath(mPath, mPaint);

        myDraw();
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        mPath.reset();
        myDraw();
        saveBuffer();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_layout:
                finish();
                break;
            case R.id.sign_commit_btn:
                try {

                    saveBitmap(mBitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.resigname_btn:

                break;

        }
    }
}
