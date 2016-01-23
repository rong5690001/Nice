package com.nice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.nice.R;
import com.nice.httpapi.NiceRxApi;
import com.nice.widget.NiceImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Bind(R.id.rem_layout)
    LinearLayout remLayout;

    @Bind(R.id.rem_icon)
    NiceImageView remIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceRxApi.login();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(LoginActivity.this, "sub", Toast.LENGTH_SHORT).show();
                NiceRxApi.login();
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
        switch (v.getId()){
            case R.id.rem_layout:
                remOnclick();
                break;
        }
    }

    private void remOnclick(){
        if("rem".equals(remIcon.getTag())){
            remIcon.setImageResource(R.mipmap.radio);
            remIcon.setTag(null);
        } else {
            remIcon.setImageResource(R.mipmap.radio_check);
            remIcon.setTag("rem");
        }
    }
}
