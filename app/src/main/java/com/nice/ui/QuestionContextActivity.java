package com.nice.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.adapter.QuestionContextAdapter;
import com.nice.ui.fragment.GroupListFragment;
import com.nice.widget.NiceCompletionNormalView;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    FragmentManager fm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_context);
        ButterKnife.bind(this);

        fm = getSupportFragmentManager();
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        frameLayout.setLayoutManager(manager);
//        QuestionContextAdapter adapter = new QuestionContextAdapter(new ArrayList<String>(), this
//                , R.layout.item_signleselect
//                , R.layout.item_selectinstruction,
//                R.layout.view_completion_normal);
//        frameLayout.setAdapter(adapter);
        initLayout();
    }

    private void initLayout() {
        title.setText("问卷调查");
        rightBtnLayout.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.group_btn:
                showGroup();
                break;
            case R.id.back_layout:
                finish();
                break;


        }
    }

    public void showGroup(){
        GroupListFragment gpFragment = new GroupListFragment();
        FragmentTransaction gp = fm.beginTransaction();
        gp.replace(R.id.frame_layout,gpFragment);
        gp.commit();
//        getSupportFragmentManager()
    }
}
