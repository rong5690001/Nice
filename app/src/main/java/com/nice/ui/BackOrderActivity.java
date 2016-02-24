package com.nice.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.httpapi.NiceRxApi;
import com.nice.model.NicetOrderInfo;
import com.nice.model.NicetSheet;
import com.nice.util.QuestionUtil;
import com.nice.widget.NiceButton;
import com.nice.widget.NiceEditText;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Subscriber;

public class BackOrderActivity extends AppCompatActivity implements View.OnClickListener {

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
    @Bind(R.id.text)
    NiceTextView text;
    @Bind(R.id.backorder_info)
    NiceEditText backorderInfo;
    @Bind(R.id.order_back_btn)
    NiceButton orderBackBtn;
    @Bind(R.id.order_go_btn)
    NiceButton orderGoBtn;

    private NicetOrderInfo orderInfo;
    private NicetSheet nicetSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_order);
        ButterKnife.bind(this);
        initLayout();
        orderInfo = (NicetOrderInfo) getIntent().getSerializableExtra("entity");
        nicetSheet = (NicetSheet) getIntent().getSerializableExtra("nicetSheet");
    }

    private void initLayout() {
        title.setText("问卷调查");
        rightBtnLayout.setVisibility(View.GONE);
    }

    private void commit() {
        if (TextUtils.isEmpty(backorderInfo.getText().toString())) {
            Toast.makeText(NiceApplication.instance(), "请填写退回原因", Toast.LENGTH_SHORT).show();
            return;
        }

        NiceRxApi.backOrder(orderInfo.oiId, backorderInfo.getText().toString()).subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {
                if (QuestionUtil.delQuestion(String.valueOf(nicetSheet.shId))) {
                    Toast.makeText(NiceApplication.instance(), "已提交申请", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post("QuestionNoteActivity.finish");
                    finish();
                } else {
                    Toast.makeText(NiceApplication.instance(), "提交失败，请重试", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_back_btn:
                finish();
                break;
            case R.id.order_go_btn:
                commit();
                break;
            case R.id.back_layout:
                finish();
                break;
        }
    }
}
