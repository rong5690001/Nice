package com.nice.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.nice.R;
import com.nice.model.NicetSheet;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuestionSignActivity extends AppCompatActivity implements View.OnClickListener {


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
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.quest_sign_takephoto)
    NiceImageView questSignTakephoto;
    @Bind(R.id.quest_sign_retakephoto)
    NiceImageView questSignRetakephoto;
    @Bind(R.id.rephoto_btn)
    LinearLayout rephotoBtn;
    @Bind(R.id.sign_commit_btn)
    LinearLayout signCommitBtn;
    @Bind(R.id.sign_bottom_tab)
    LinearLayout signBottomTab;
    private NicetSheet entity;
    private AMap aMap;
    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_sign);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        entity = (NicetSheet) getIntent().getSerializableExtra("entity");
//        if (null != entity)
        initLayout();

        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
//        aMap.setMyLocationEnabled(true);
    }

    private void initLayout() {
        if(aMap == null){
            System.out.print("sssssssssssssssssssssssssss");
            aMap = map.getMap();
            System.out.print("ddddddddddddddddddddddddd");
        }
    }



    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

            FileOutputStream b = null;
            File file = new File("/sdcard/Image/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/Image/" + name;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
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


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_commit_btn:
                Intent intent1 = new Intent(QuestionSignActivity.this, QuestionContextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity", entity);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.quest_sign_takephoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                break;
            case R.id.quest_sign_retakephoto:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, 1);
                break;
        }
    }
}
