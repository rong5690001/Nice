package com.nice.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nice.R;
import com.nice.httpapi.NiceRxApi;

public class LoadingDialog_hq extends Dialog {

    public LoadingDialog_hq(Context context) {
        super(context, R.style.loadingDialogStyle);
    }

    private LoadingDialog_hq(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_hq);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
    }
}

