package com.nice.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.httpapi.NiceRxApi;
import com.nice.httpapi.response.dataparser.NiceNewQuestPaser;
import com.nice.model.NiceQuestion;
import com.nice.ui.fragment.MainFragment;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, View.OnClickListener {

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
    @Bind(R.id.num_new)
    NiceTextView numNew;
    @Bind(R.id.arrows_new)
    NiceImageView arrowsNew;
    @Bind(R.id.new_contrainer)
    LinearLayout newContrainer;
    @Bind(R.id.num_incomplete)
    NiceTextView numIncomplete;
    @Bind(R.id.arrows_incomplete)
    NiceImageView arrowsIncomplete;
    @Bind(R.id.incomplete_contrainer)
    LinearLayout incompleteContrainer;
    @Bind(R.id.num_uploaded)
    NiceTextView numUploaded;
    @Bind(R.id.arrows_uploaded)
    NiceImageView arrowsUploaded;
    @Bind(R.id.uploaded_contrainer)
    LinearLayout uploadedContrainer;

    private List<NiceQuestion> niceQuestionList;
    private List<NiceQuestion> uploadQuestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        isStorageOpen(); //判断访问内存权限是否打开
        ifGPGOpen(); //判断GPS权限是否开启
        isTakePhotoOpen(); //判断CMAERA权限是否开启
        initLayout();
    }
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    //判断GPS权限是否开启
    private void ifGPGOpen() {
        int hasWriteGPSPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteGPSPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (hasWriteGPSPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showMessageOKCancel("您需要打开GPS权限",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
    }

    //判断照相机权限是否打开
    private void isTakePhotoOpen(){
        int hasWriteCameraermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteCameraermission = checkSelfPermission(Manifest.permission.CAMERA);
        }
        if (hasWriteCameraermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showMessageOKCancel("您需要打开CAMERA权限",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[] {Manifest.permission.CAMERA},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
    }
    //判断访问内存权限是否打开
    private void isStorageOpen(){
        int hasWriteCameraermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteCameraermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (hasWriteCameraermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("您需要打开文件读取权限",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInCompleteQuest();
        getNewQuestInfo();
        getUploadInfo();
    }

    private void initLayout() {
        title.setText("问卷");
        backLayout.setVisibility(View.GONE);
    }

    private void getInCompleteQuest() {
        String[] shIdsStr = null;
        String ids = NiceApplication.instance()
                .getQuestPreferencesQuest()
                .getString("quest_ids", "");
        if(!TextUtils.isEmpty(ids)){
            shIdsStr = ids.split(",");
        }

        numIncomplete.setText(null == shIdsStr ? "0份" : shIdsStr.length + "份");
    }

    private void getUploadInfo() {
        NiceRxApi.getUploadedQuestion().subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {
                uploadQuestionList = NiceNewQuestPaser.paser(jsonObject);
                numUploaded.setText(null == uploadQuestionList ? "0份" : String.valueOf(uploadQuestionList.size()) + "份");
            }
        });
    }

    private void getNewQuestInfo() {
        NiceRxApi.getNewQuestionInfo().subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {
                niceQuestionList = NiceNewQuestPaser.paser(jsonObject);
                numNew.setText(null == niceQuestionList ? "0份" : String.valueOf(niceQuestionList.size()) + "份");
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_layout:
                finish();
                break;
            case R.id.right_btn_layout:
                finish();
                break;
            case R.id.new_contrainer:
                startActivity(new Intent(MainActivity.this, NewQuestActivity.class));
                break;
            case R.id.incomplete_contrainer:
                startActivity(new Intent(MainActivity.this, IncompleteQuestionActivity.class));
                break;
            case R.id.uploaded_contrainer:
                startActivity(new Intent(MainActivity.this, QuestionUploadActivity.class));
                break;
        }

    }
}
