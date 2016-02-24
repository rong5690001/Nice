package com.nice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.adapter.IncompleteQuestionAdapter;
import com.nice.adapter.NewQuestionAdapter;
import com.nice.httpapi.NiceRxApi;
import com.nice.httpapi.response.dataparser.NiceSheetPaser;
import com.nice.model.NiceQuestion;
import com.nice.model.NicetSheet;
import com.nice.util.QuestionUtil;
import com.nice.widget.NiceButton;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rx.Subscriber;

public class NewQuestActivity extends AppCompatActivity implements View.OnClickListener{


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
    @Bind(R.id.all_choose_icon)
    NiceImageView allChooseIcon;
    @Bind(R.id.all_choose_layout)
    RelativeLayout allChooseLayout;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.submit_btn)
    NiceButton submitBtn;

    private NewQuestionAdapter adapter;

    int i=0;
    ProgressBar progressBar=null;
    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    submitBtn.setClickable(false);
                    i+=((Math.random()+1)*10);
                    if(i>=100){
                        i=100;
                    }
                    progressBar.setProgress(i);
                    if(i!=100){
                        handler.sendEmptyMessageDelayed(0x123,500);
                        submitBtn.setText("正在下载"+i+"%");
                    }else if(i==100){
                        submitBtn.setText("下载完成");
                        handler.sendEmptyMessageDelayed(0x321,500);
                        startActivity(new Intent(NewQuestActivity.this, IncompleteQuestionActivity.class));
                        finish();
                    }
                    break;
//                case 0x321:
//                    submitBtn.setText(打开);
//                    submitBtn.setClickable(true);
//                    submitBtn.setBackgroundResource(R.drawable.aa_button_after);
//                    handler.sendEmptyMessageDelayed(0x110,1000);
//                    break;
                case 0x110:
                    progressBar.setProgress(0);
                    submitBtn.setBackgroundResource(R.drawable.btn_selector);
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quest);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initLayout();

        adapter = new NewQuestionAdapter(this, new ArrayList<NicetSheet>(), R.layout.item_new_question);

        listView.setAdapter(adapter);

        getData();
    }

    private void initLayout(){
        title.setText("新问卷");
        rightBtnLayout.setVisibility(View.GONE);
    }

    private void getData(){
        NiceRxApi.getNewQuestion().subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(JSONObject jsonObject) {
                List<NicetSheet> sheets = NiceSheetPaser.paser(jsonObject);
                adapter.replaceAll(sheets);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_layout:
                finish();
                break;
            case R.id.all_choose_layout:
                allChooseIcon.setSelected(!allChooseIcon.isSelected());
                if(allChooseIcon.isSelected()){
                    adapter.allSelected();
                }else{
                    adapter.clearAllSelected();
                }
                break;
            case R.id.submit_btn:
                if(adapter.getSelected().size() <= 0 ){
                    Toast.makeText(NiceApplication.instance(), "请至少选择一个问卷", Toast.LENGTH_SHORT).show();
                    return;
                }
                i= 0;
                NiceRxApi.Download(adapter.getSelected()).subscribe(new Subscriber<JSONObject>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        boolean isSaved = QuestionUtil.saveQuestion(jsonObject);
                        if(isSaved){
                            handler.sendEmptyMessage(0x123);
//                            Toast.makeText(NiceApplication.instance(), "下载完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                if(submitBtn.getText().equals("下载完成")){
//                    startActivity(new Intent(NewQuestActivity.this, IncompleteQuestionActivity.class));
//                }
                break;
        }
    }

    public void onEventMainThread(String event){
        if ("allSelected".equals(event)) {
            allChooseIcon.setSelected(true);
        }
        if ("removeAllSelected".equals(event)) {
            allChooseIcon.setSelected(false);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
