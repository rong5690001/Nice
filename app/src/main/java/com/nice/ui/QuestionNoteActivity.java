package com.nice.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.httpapi.NiceRxApi;
import com.nice.httpapi.response.dataparser.NiceOrderInfoPaser;
import com.nice.model.NicetOrderInfo;
import com.nice.model.NicetSheet;
import com.nice.util.QuestionUtil;
import com.nice.util.StringUtils;
import com.nice.widget.NiceButton;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Subscriber;

public class QuestionNoteActivity extends AppCompatActivity implements OnClickListener {


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
    @Bind(R.id.info_back_btn)
    NiceButton infoBackBtn;
    @Bind(R.id.info_go_btn)
    NiceButton infoGoBtn;
    @Bind(R.id.quest_id)
    NiceTextView questId;
    @Bind(R.id.quest_completeness)
    NiceTextView questCompleteness;
    @Bind(R.id.quest_name)
    NiceTextView questName;
    @Bind(R.id.address)
    NiceTextView address;
    @Bind(R.id.username)
    NiceTextView username;
    @Bind(R.id.phone)
    NiceTextView phone;
    @Bind(R.id.date)
    NiceTextView date;
    @Bind(R.id.remark)
    LinearLayout remark;
    @Bind(R.id.remark_textview)
    NiceTextView remarkTextview;

    private NicetSheet entity;
    private NicetOrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_note);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        entity = (NicetSheet) getIntent().getSerializableExtra("entity");
        if (null != entity)
            initLayout();
        getSheetInfo();
    }

    private void initLayout() {
        title.setText("问卷调查");
        rightBtnLayout.setVisibility(View.GONE);

        questId.setText(String.valueOf(entity.shId));
        questName.setText(entity.shName);

        questCompleteness.setText(String.valueOf(QuestionUtil.getCompleteness(entity)) + "%");
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
                address.setText(orderInfo.oiLocation);
                username.setText(orderInfo.oiConName);
                phone.setText(orderInfo.oiConPhone);
                date.setText(StringUtils.formatDate(orderInfo.oiStartDate)
                        + "至" + StringUtils.formatDate(orderInfo.oiEndDate));
                remarkTextview.setText(orderInfo.oiDescription);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_back_btn:
                MyAlertDialog dialog = new MyAlertDialog.Builder(QuestionNoteActivity.this)
                .setMessage("您确定要退回订单吗？",R.color.black)
                .setNegativeButton("否",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                    }
                })
                .setPositiveButton("是",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(QuestionNoteActivity.this, BackOrderActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("entity", orderInfo);
                        bundle1.putSerializable("nicetSheet", entity);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        dialog.dismiss();
                    }
                }).create();
                dialog.show();

                break;
            case R.id.back_layout:
                finish();
                break;
            case R.id.info_go_btn:
                Intent intent = new Intent(QuestionNoteActivity.this, QuestionSignActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity", entity);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;

        }
    }

    public void onEventMainThread(String event) {
        if ("QuestionNoteActivity.finish".equals(event)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
