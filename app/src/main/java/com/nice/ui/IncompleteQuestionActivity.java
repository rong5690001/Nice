package com.nice.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.adapter.IncompleteQuestionAdapter;
import com.nice.model.NiceQuestion;
import com.nice.model.NicetSheet;
import com.nice.util.QuestionUtil;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiao on 2016/1/21.
 */
public class IncompleteQuestionActivity extends AppCompatActivity implements View.OnClickListener {


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

    private IncompleteQuestionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_incomplete_quest);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout(){
        title.setText("未完成问卷");
        rightBtnLayout.setVisibility(View.GONE);

        mAdapter = new IncompleteQuestionAdapter(this, getData(), R.layout.item_incomplete_question);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = NiceApplication.instance()
                        .getQuestValuePreferencesQuest().getString(String.valueOf(mAdapter.getItem(position).shId), "");
                Intent intent = null;
                if(TextUtils.isEmpty(value)) {
                    intent = new Intent(IncompleteQuestionActivity.this, QuestionNoteActivity.class);
                }else{
                    intent = new Intent(IncompleteQuestionActivity.this, QuestionContextActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("entity", mAdapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private List<NicetSheet> getData(){

        return QuestionUtil.getQusetions();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_layout:
                finish();
                break;
        }
    }
}
