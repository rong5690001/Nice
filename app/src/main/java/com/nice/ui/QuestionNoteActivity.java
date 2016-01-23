package com.nice.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.nice.R;

public class QuestionNoteActivity extends AppCompatActivity implements OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_note);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_go_btn:
                startActivity(new Intent(QuestionNoteActivity.this, QuestionSignActivity.class));
                break;

        }
    }
}
