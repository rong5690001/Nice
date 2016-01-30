package com.nice.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.model.Event.SwitchGroupEvent;
import com.nice.model.NicetSheet;
import com.nice.ui.fragment.ExamFragment;
import com.nice.ui.fragment.GroupListFragment;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class QuestionContextActivity extends AppCompatActivity implements View.OnClickListener {

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
    @Bind(R.id.group_btn)
    LinearLayout groupBtn;
    @Bind(R.id.siweep_btn)
    LinearLayout siweepBtn;
    @Bind(R.id.sign_bottom_tab)
    LinearLayout signBottomTab;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;

    @Bind(R.id.quest_id)
    NiceTextView questId;
    @Bind(R.id.quest_completeness)
    NiceTextView questCompleteness;

    private NicetSheet entity;
    //第几个分组
    private int groupIndex;
    public boolean isLastGroup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_context);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        entity = (NicetSheet) getIntent().getSerializableExtra("entity");
        NiceApplication.instance().shId = entity.shId;
        Log.e(QuestionSignActivity.class.getSimpleName(), "订单ID：" + entity.shId);
        if (null != entity)
            initLayout();
    }

    private void initLayout() {
        title.setText("问卷调查");
        rightBtnLayout.setVisibility(View.GONE);

        questId.setText(String.valueOf(entity.shId));
        questCompleteness.setText("50%");

        showExamFragment();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_btn:
                showGroup();
                break;
            case R.id.back_layout:
                finish();
                break;
        }
    }

    public void showGroup() {
        GroupListFragment gpFragment = GroupListFragment.newInstance(entity);
        FragmentTransaction gp = getSupportFragmentManager().beginTransaction();
        gp.replace(R.id.frame_layout, gpFragment);
        gp.commit();
//        getSupportFragmentManager()
    }

    public void showExamFragment() {
        if((entity.SheetQuestionGroup.size() - 1) == groupIndex){
            isLastGroup = true;
            showCommitBtn();
        }else{
            hideCommitBtn();
        }
        ExamFragment examFragment = ExamFragment.newInstance(entity.SheetQuestionGroup.get(groupIndex), isLastGroup);
        FragmentTransaction gp = getSupportFragmentManager().beginTransaction();
        gp.replace(R.id.frame_layout, examFragment);
        gp.commit();
    }

    public void hideCommitBtn(){
        if(rightBtnLayout.getVisibility() == View.VISIBLE){
            rightBtnLayout.setVisibility(View.GONE);
            return;
        }
    }

    public void showCommitBtn(){
        rightBtnLayout.setVisibility(View.VISIBLE);
        rightIcon.setImageResource(R.mipmap.checkmark_white);
        rightText.setText("提交");
        rightBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void onEventMainThread(String event) {
        if (event.equals("addGroupIndex")) {
            groupIndex++;
            if (groupIndex > entity.SheetQuestionGroup.size() - 1)
                return;
            showExamFragment();
        }
        if (event.equals("reduceGroupIndex")) {
            groupIndex--;
            if (groupIndex < 0) return;
            showExamFragment();
        }
    }

    public void onEventMainThread(SwitchGroupEvent event) {
        groupIndex = event.groupIndex;
        showExamFragment();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
