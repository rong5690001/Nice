package com.nice.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.nice.R;
import com.nice.adapter.FinishQuestAdapter;
import com.nice.model.NicetSheet;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class FinishQuestActivity extends AppCompatActivity implements View.OnClickListener{

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

    FinishQuestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_quest);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout(){
        title.setText("已上传问卷");
        rightBtnLayout.setVisibility(View.GONE);
        adapter = new FinishQuestAdapter(this, new ArrayList<NicetSheet>(), R.layout.item_finish_question);
        listView.setAdapter(adapter);
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
