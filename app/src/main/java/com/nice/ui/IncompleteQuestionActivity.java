package com.nice.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.adapter.IncompleteQuestionAdapter;
import com.nice.model.NiceQuestion;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiao on 2016/1/21.
 */
public class IncompleteQuestionActivity extends AppCompatActivity {


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
    @Bind(R.id.recyclerview_incomplete)
    RecyclerView recyclerviewIncomplete;

    private IncompleteQuestionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_incomplete_quest);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewIncomplete.setLayoutManager(layoutManager);

        mAdapter = new IncompleteQuestionAdapter(getData(), this, R.layout.item_incomplete_question);

        recyclerviewIncomplete.setAdapter(mAdapter);
    }

    private List<NiceQuestion> getData(){
        List<NiceQuestion> datas = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            NiceQuestion niceQuestion = new NiceQuestion("this is " + i, String.valueOf(i));
            datas.add(niceQuestion);
        }

        return datas;
    }
}
