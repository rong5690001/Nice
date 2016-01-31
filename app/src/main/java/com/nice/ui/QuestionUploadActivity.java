package com.nice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.adapter.NewQuestionAdapter;
import com.nice.adapter.UploadQuestionAdapter;
import com.nice.httpapi.NiceRxApi;
import com.nice.httpapi.response.dataparser.NiceSheetPaser;
import com.nice.model.NicetSheet;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class QuestionUploadActivity extends AppCompatActivity implements View.OnClickListener{

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
    @Bind(R.id.listView)
    ListView listView;
    private UploadQuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_upload);
        ButterKnife.bind(this);
        initLayout();
        adapter = new UploadQuestionAdapter(this, new ArrayList<NicetSheet>(), R.layout.item_incomplete_question);
        listView.setAdapter(adapter);
        getData();
    }

    private void initLayout() {
        title.setText("已上传问卷");
    }


    private void getData() {
        NiceRxApi.getUploadedQuestion().subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {
                List<NicetSheet> sheets = NiceSheetPaser.paser(jsonObject);
                adapter.addAll(sheets);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_layout:
                finish();
                break;
        }
    }

}
