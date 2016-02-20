package com.nice;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.nice.model.NiceUser;

/**
 * Created by chen on 2015/12/16.
 */
public class NiceApplication extends Application {
    
    private static final String QUEST_TABLE = "quest_table";
    private static final String VALUE_TABLE = "value_table";
    private static final String COMPLETENESS = "completeness";

    private static NiceApplication instance;
    public static NiceUser user;
    private SharedPreferences preferencesQuest;
    private SharedPreferences preferencesValue;
    private SharedPreferences preferencesCompleteness;
    
    public long shId;

    public static NiceApplication instance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferencesQuest = getSharedPreferences(QUEST_TABLE, 0);
        preferencesValue = getSharedPreferences(VALUE_TABLE, 0);
        preferencesCompleteness = getSharedPreferences(COMPLETENESS, 0);
        Stetho.initializeWithDefaults(this);
    }


    public SharedPreferences.Editor getQuestEditor(){
        return preferencesQuest == null ? getSharedPreferences(QUEST_TABLE, 0).edit() : preferencesQuest.edit();
    }

    public SharedPreferences getQuestPreferencesQuest(){
        return preferencesQuest == null ? getSharedPreferences(QUEST_TABLE, 0) : preferencesQuest;
    }

    public SharedPreferences.Editor getValueEditor(){
        return preferencesQuest == null ? getSharedPreferences(VALUE_TABLE, 0).edit() : preferencesValue.edit();
    }

    public SharedPreferences getQuestValuePreferencesQuest(){
        return preferencesQuest == null ? getSharedPreferences(VALUE_TABLE, 0) : preferencesValue;
    }
    
    public SharedPreferences.Editor getCompletenessEditor(){
        return preferencesCompleteness == null ? getSharedPreferences(COMPLETENESS, 0).edit()
                : preferencesCompleteness.edit();
    }

    public SharedPreferences getPreferencesCompleteness(){
        return preferencesCompleteness == null ? getSharedPreferences(COMPLETENESS, 0) : preferencesCompleteness;
    }
}
