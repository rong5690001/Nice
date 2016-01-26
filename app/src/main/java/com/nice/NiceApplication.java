package com.nice;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.nice.model.NiceUser;

import java.util.prefs.PreferencesFactory;

/**
 * Created by chen on 2015/12/16.
 */
public class NiceApplication extends Application {

    private static NiceApplication instance;
    public static NiceUser user;
    private SharedPreferences preferences;

    public static NiceApplication instance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = getSharedPreferences("nice", 0);
        Stetho.initializeWithDefaults(this);
    }


    public SharedPreferences.Editor getEditor(){
        return preferences == null ? getSharedPreferences("nice", 0).edit() : preferences.edit();
    }

    public SharedPreferences getPreferences(){
        return preferences == null ? getSharedPreferences("nice", 0) : preferences;
    }
}
