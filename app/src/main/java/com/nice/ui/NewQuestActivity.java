package com.nice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.widget.NiceButton;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quest);
        ButterKnife.bind(this);


    }

    private void initLayout(){
        title.setText("新问卷");
        rightBtnLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_layout:
                finish();
                break;
            case R.id.all_choose_layout:
                allChooseIcon.setSelected(!allChooseIcon.isSelected());
                break;

        }
    }
}
