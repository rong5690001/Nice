package com.nice.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.nice.R;
import com.nice.ui.fragment.MainFragment;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
