package com.nice;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by chen on 2015/12/16.
 */
public class NiceApplication extends Application {

    private static NiceApplication instance;

    public static NiceApplication instance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
    }
}
