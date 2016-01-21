package com.nice.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.nice.R;
import com.nice.ui.fragment.MainFragment;
import com.nice.widget.NiceImageView;
import com.nice.widget.NiceTextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, View.OnClickListener {

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
    @Bind(R.id.num_new)
    NiceTextView numNew;
    @Bind(R.id.arrows_new)
    NiceImageView arrowsNew;
    @Bind(R.id.new_contrainer)
    LinearLayout newContrainer;
    @Bind(R.id.num_incomplete)
    NiceTextView numIncomplete;
    @Bind(R.id.arrows_incomplete)
    NiceImageView arrowsIncomplete;
    @Bind(R.id.incomplete_contrainer)
    LinearLayout incompleteContrainer;
    @Bind(R.id.num_uploaded)
    NiceTextView numUploaded;
    @Bind(R.id.arrows_uploaded)
    NiceImageView arrowsUploaded;
    @Bind(R.id.uploaded_contrainer)
    LinearLayout uploadedContrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_layout:
                finish();
                break;
            case R.id.right_btn_layout:
                finish();
                break;
            case R.id.new_contrainer:
                startActivity(new Intent(MainActivity.this, NewQuestActivity.class));
                break;
            case R.id.incomplete_contrainer:
                startActivity(new Intent(MainActivity.this, IncompleteQuestionActivity.class));
                break;

        }

    }
}
