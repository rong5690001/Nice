package com.nice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nice.R;

public class QuestionSignActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_sign);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_commit_btn:
                startActivity(new Intent(QuestionSignActivity.this, QuestionContextActivity.class));
                break;

        }
    }
}
