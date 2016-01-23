package com.nice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.adapter.NewQuestionAdapter;
import com.nice.model.NiceQuestion;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewQuestActivity extends AppCompatActivity {

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
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    private NewQuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quest);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        mAdapter = new NewQuestionAdapter(getData(), this, R.layout.item_new_question);

        recyclerview.setAdapter(mAdapter);

    }

    private List<NiceQuestion> getData(){
        List<NiceQuestion> datas = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            NiceQuestion niceQuestion = new NiceQuestion("this is " + i, String.valueOf(i));
            datas.add(niceQuestion);
        }

        return datas;
    }

}
