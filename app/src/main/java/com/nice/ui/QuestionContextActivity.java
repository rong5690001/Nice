package com.nice.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nice.NiceApplication;
import com.nice.R;
import com.nice.httpapi.NiceRxApi;
import com.nice.httpapi.gson.QSGsonFactory;
import com.nice.httpapi.response.dataparser.NiceOrderInfoPaser;
import com.nice.model.Event.SqIdEvent;
import com.nice.model.Event.SwitchGroupEvent;
import com.nice.model.FileModel;
import com.nice.model.NicetOrderInfo;
import com.nice.model.NicetSheet;
import com.nice.model.SignInModel;
import com.nice.ui.fragment.ExamFragment;
import com.nice.ui.fragment.GroupListFragment;
import com.nice.util.FileUtil;
import com.nice.util.QuestionUtil;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Subscriber;

public class QuestionContextActivity extends AppCompatActivity implements View.OnClickListener {

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
    @Bind(R.id.group_btn)
    LinearLayout groupBtn;
    @Bind(R.id.siweep_btn)
    LinearLayout siweepBtn;
    @Bind(R.id.sign_bottom_tab)
    LinearLayout signBottomTab;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;

    @Bind(R.id.quest_id)
    NiceTextView questId;
    @Bind(R.id.quest_completeness)
    NiceTextView questCompleteness;

    public NicetSheet entity;
    private NicetOrderInfo orderInfo;
    private ExamFragment examFragment;
    //判断签到时是否离线签到 0否，有网已签到 1 是 没有网 签到没有提交
    public int isSign;
    public SignInModel signInModel;
//    public double mysilongitude;
//    public double mysilatitude;
//    public String mysiadd;
//    public String mysipicurl;
//    public String mysiTime;
    //第几个分组
    public int groupIndex;
    public int isLastGroup = 0;//0:第一个分组 1:中间的分组 2:最后一个分组

    private String sqId;
    private LoadingDialog dialogs;
    public static String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_context);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        entity = (NicetSheet) getIntent().getSerializableExtra("entity");
        isSign = getIntent().getExtras().getInt("mystr");
        if(isSign == 1){
            signInModel = (SignInModel) getIntent().getSerializableExtra("signInModel");
            SharedPreferences mSharedPreferences = getSharedPreferences("sign", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            try {
                editor.putString("signInModel",new JSONObject(QSGsonFactory.create().toJson(signInModel)).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editor.putInt("isSign",isSign);
            editor.commit();
        }
        NiceApplication.instance().shId = entity.shId;
        Log.e(QuestionSignActivity.class.getSimpleName(), "订单ID：" + entity.shId);
        if (null != entity)
            initLayout();
        getSheetInfo();
    }

    private void initLayout() {
        title.setText("问卷调查");
        rightBtnLayout.setVisibility(View.GONE);

        questCompleteness.setText(String.valueOf(QuestionUtil.getCompleteness(entity)) + "%");
        showExamFragment();
    }

    private void getSheetInfo() {
        NiceRxApi.getSheetInfo(entity.shId).subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {
                orderInfo = NiceOrderInfoPaser.paser(jsonObject).get(0);
                questId.setText(orderInfo.oiInternalCode);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_btn:
                showGroup();
                break;
            case R.id.back_layout:
                finish();
                break;
            case R.id.siweep_btn:
                startActivity(new Intent(QuestionContextActivity.this, CaptureActivity.class));
                break;
            case R.id.info_go_btn:
                if ((entity.SheetQuestionGroup.size() - 1) == groupIndex) {
                    isLastGroup = 2;
                    boolean isSaved = examFragment.saveValues();
                    if (!isSaved) {
                        Toast.makeText(NiceApplication.instance(), "保存本地失败", Toast.LENGTH_SHORT).show();
                    } else {
                        NiceRxApi.commitQuestion(entity).subscribe(new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {
                                QuestionUtil.delQuestion(String.valueOf(entity.shId));
                                Toast.makeText(NiceApplication.instance(), "上传成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(QuestionContextActivity.this, QuestionUploadActivity.class));
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {

                            }
                        });

                    }
                }
        }
    }

    public void showGroup() {
        GroupListFragment gpFragment = GroupListFragment.newInstance(entity);
        FragmentTransaction gp = getSupportFragmentManager().beginTransaction();
        gp.replace(R.id.frame_layout, gpFragment);
        gp.commit();
//        getSupportFragmentManager()
    }

    public void showExamFragment() {
        if (groupIndex == 0) {
            isLastGroup = 0;
        } else {
            isLastGroup = 1;
        }
        if ((entity.SheetQuestionGroup.size() - 1) == groupIndex) {
            isLastGroup = 2;
            showCommitBtn();
        } else {
            hideCommitBtn();
        }
        examFragment = ExamFragment.newInstance(entity.shId, entity.SheetQuestionGroup.get(groupIndex), isLastGroup);
        FragmentTransaction gp = getSupportFragmentManager().beginTransaction();
        gp.replace(R.id.frame_layout, examFragment);
        gp.commit();
    }

    public void hideCommitBtn() {
        if (rightBtnLayout.getVisibility() == View.VISIBLE) {
            rightBtnLayout.setVisibility(View.GONE);
            return;
        }
    }

    public void showCommitBtn() {
        rightBtnLayout.setVisibility(View.VISIBLE);
        rightIcon.setImageResource(R.mipmap.checkmark_white);
        rightText.setText("提交");
        rightBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSaved = examFragment.saveValues();
                if (!isSaved) {
                    Toast.makeText(NiceApplication.instance(), "保存本地失败", Toast.LENGTH_SHORT).show();
                } else {
                    commit();
                }
            }
        });
    }

    private void commit(){
        dialogs = new LoadingDialog(this);
        dialogs.setCanceledOnTouchOutside(false);
        if(QuestionUtil.getCompleteness(entity) < 100) {
            com.nice.ui.MyAlertDialog.Builder dialog = new com.nice.ui.MyAlertDialog.Builder(QuestionContextActivity.this);
            dialog .setMessage("您在问卷中有信息未填写！请确认问卷是否已全部完成。问题提交后，您将无法对其进行任何操作",R.color.red);
            dialog.setNegativeButton("返回操作",new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //设置你的操作事项
                }
            });
            dialog.setPositiveButton("确认提交",new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialogs.show();
                    SharedPreferences  sp = getSharedPreferences("sign", Context.MODE_PRIVATE);

                    isSign = sp.getInt("isSign",0);
                    if(isSign == 1){
                        signInModel = QSGsonFactory.create().fromJson(sp.getString("signInModel",""),SignInModel.class);
                        String fileName = signInModel.sipicurl.substring(14);
                        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Image/" + fileName);
                        FileModel fileModel = new FileModel();
                        fileModel.filename = new File(fileName).getName();
                        try {
                            fileModel.file = new String(Base64.encode(FileUtil.Bitmap2Bytes(bitmap), 0), "GB2312");
                            signInModel.files.clear();
                            signInModel.files.add(fileModel);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        NiceRxApi.signIn(signInModel).subscribe(new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {

                                try {
                                    if(jsonObject.get("status").equals("1")){
                                        SharedPreferences.Editor editor = NiceApplication.instance().getPreferencesSign().edit();
                                        editor.putBoolean(String.valueOf(entity.shId), true);
                                    } else {
                                        Toast.makeText(NiceApplication.instance(), "签到失败请重试", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(NiceApplication.instance(), "签到失败请重试", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            NiceRxApi.commitQuestion(entity).subscribe(new Subscriber<JSONObject>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.get("status").equals("1")){
                                            dialogs.dismiss();
                                            QuestionUtil.delQuestion(String.valueOf(entity.shId));
                                            Toast.makeText(NiceApplication.instance(), "上传成功", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(QuestionContextActivity.this, QuestionUploadActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(NiceApplication.instance(), "上传失败，请重试", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(NiceApplication.instance(), "上传失败，请重试", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();

                }
            });
            dialog.create().show();
        } else {
            dialogs.show();
            SharedPreferences  sp = getSharedPreferences("sign", Context.MODE_PRIVATE);
            isSign = sp.getInt("isSign",0);
            if(isSign == 1){
                signInModel = QSGsonFactory.create().fromJson(sp.getString("signInModel",""),SignInModel.class);
                String fileName = signInModel.sipicurl.substring(14);
                Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Image/" + fileName);
                FileModel fileModel = new FileModel();
                fileModel.filename = new File(fileName).getName();
                try {
                    fileModel.file = new String(Base64.encode(FileUtil.Bitmap2Bytes(bitmap), 0), "GB2312");
                    signInModel.files.clear();
                    signInModel.files.add(fileModel);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                NiceRxApi.signIn(signInModel).subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {

                        try {
                            if(jsonObject.get("status").equals("1")){
                                SharedPreferences.Editor editor = NiceApplication.instance().getPreferencesSign().edit();
                                editor.putBoolean(String.valueOf(entity.shId), true);
                            } else {
                                Toast.makeText(NiceApplication.instance(), "签到失败请重试", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(NiceApplication.instance(), "签到失败请重试", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NiceRxApi.commitQuestion(entity).subscribe(new Subscriber<JSONObject>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            NiceRxApi.commitQuestion(entity).subscribe(new Subscriber<JSONObject>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(JSONObject jsonObject) {
                                    try {
                                        if(jsonObject.get("status").equals("1")){
                                            dialogs.dismiss();
                                            QuestionUtil.delQuestion(String.valueOf(entity.shId));
                                            Toast.makeText(NiceApplication.instance(), "上传成功", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(QuestionContextActivity.this, QuestionUploadActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(NiceApplication.instance(), "上传失败，请重试", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(NiceApplication.instance(), "上传失败，请重试", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onNext(JSONObject jsonObject) {
                            try {
                                if(jsonObject.get("status").equals("1")){
                                    dialogs.dismiss();
                                    QuestionUtil.delQuestion(String.valueOf(entity.shId));
                                    Toast.makeText(NiceApplication.instance(), "上传成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(QuestionContextActivity.this, QuestionUploadActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(NiceApplication.instance(), "上传失败，请重试", Toast.LENGTH_SHORT).show();
                                    dialogs.dismiss();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(NiceApplication.instance(), "上传失败，请重试", Toast.LENGTH_SHORT).show();
                                dialogs.dismiss();
                            }
                        }
                    });
                }
            }).start();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            System.out.println("imgUrl:" + imgUrl);
            String fileName = FileUtil.savePhoto(FileUtil.getBitmapFromUrl(imgUrl), sqId);
            System.out.println("fileName:" + fileName);
            if (!TextUtils.isEmpty(fileName)) {
                examFragment.addValue(sqId, fileName);
                examFragment.notifyDateChange();

            }
        }

        if (resultCode == 1000) {
            String fileName = data.getStringExtra("fileName");
            String msqId = data.getStringExtra("sqId");
            if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(msqId)) {
                examFragment.addValue(msqId, fileName);
                examFragment.saveIsEnable();
                examFragment.notifyDateChange();
            }
        }
    }

    public void onEventMainThread(String event) {
        if (event.equals("addGroupIndex")) {
            questCompleteness.setText(String.valueOf(QuestionUtil.getCompleteness(entity)) + "%");
            groupIndex++;
            if (groupIndex > entity.SheetQuestionGroup.size() - 1)
                return;
            showExamFragment();
        }
        if (event.equals("reduceGroupIndex")) {
            questCompleteness.setText(String.valueOf(QuestionUtil.getCompleteness(entity)) + "%");
            groupIndex--;
            if (groupIndex < 0) return;
            showExamFragment();
        }

        if(event.equals("commit")){
            commit();
        }
    }

    public void onEventMainThread(SwitchGroupEvent event) {
        groupIndex = event.groupIndex;
        showExamFragment();
    }

    public void onEventMainThread(SqIdEvent event) {
        sqId = event.sqId;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
