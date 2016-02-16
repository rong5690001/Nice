package com.nice.ui;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nice.R;

public class ShowDecode extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.show);
		
		Intent intent = getIntent();
		TextView tt = (TextView)findViewById(R.id.showDecode);
		Log.d("jiaojiabin---decode", intent.getStringExtra("decode"));
		tt.setText(intent.getStringExtra("decode"));
	}
}
