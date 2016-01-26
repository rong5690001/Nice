package com.nice.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.httpapi.NiceRxApi;
import com.nice.model.NiceUser;
import com.nice.widget.NiceEditText;
import com.nice.widget.NiceImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.functions.Func1;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.rem_layout)
    LinearLayout remLayout;
    @Bind(R.id.rem_icon)
    NiceImageView remIcon;
    @Bind(R.id.uiCode)
    NiceEditText uiCode;
    @Bind(R.id.uiPassword)
    NiceEditText uiPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                final String uiCode = LoginActivity.this.uiCode.getText().toString().trim();
//                final String uiPassword = LoginActivity.this.uiPassword.getText().toString();
//                if(TextUtils.isEmpty(uiCode) || TextUtils.isEmpty(uiPassword)){
//                    Toast.makeText(NiceApplication.instance(), "请填写用户名和密码", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                NiceRxApi.login(uiCode, uiPassword).map(new Func1<NiceUser, NiceUser>() {
//                    @Override
//                    public NiceUser call(NiceUser niceUser) {
//                        NiceApplication.instance().user = niceUser;
//                        return niceUser;
//                    }
//                }).subscribe(new Subscriber<NiceUser>() {
//                    @Override
//                    public void onCompleted() {
//                        if(remIcon.isSelected()){
//                            SharedPreferences.Editor editor = NiceApplication.instance().getEditor();
//                            editor.putString("uiCode", uiCode);
//                            editor.putString("uiPassword", uiPassword);
//                            editor.commit();
//                        }
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(NiceApplication.instance(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(NiceUser niceUser) {
//
//                    }
//                });

                Toast.makeText(LoginActivity.this, "sub", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rem_layout:
                remOnclick();
                break;
        }
    }

    private void remOnclick() {
        if ("rem".equals(remIcon.getTag())) {
            remIcon.setImageResource(R.mipmap.radio);
            remIcon.setTag(null);
            remIcon.setSelected(false);
        } else {
            remIcon.setImageResource(R.mipmap.radio_check);
            remIcon.setTag("rem");
            remIcon.setSelected(true);
        }
    }
}
