package com.nice;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.nice.model.NiceUser;

/**
 * Created by chen on 2015/12/16.
 */
public class NiceApplication extends Application {
    
    private static final String USER_TABLE = "user_table";
    private static final String QUEST_TABLE = "quest_table";
    private static final String VALUE_TABLE = "value_table";
    private static final String COMPLETENESS = "completeness";
    private static final String SIGN = "sign";
    private static final String GROUPINDEX = "groupIndex";

    private static NiceApplication instance;
    public static NiceUser user;
    private SharedPreferences preferencesUser;
    private SharedPreferences preferencesQuest;
    private SharedPreferences preferencesValue;
    private SharedPreferences preferencesCompleteness;
    private SharedPreferences preferencesSign;
    private SharedPreferences preferencesGroupIndex;

    public long shId;

    public static NiceApplication instance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
    }

    public SharedPreferences.Editor getUserEditor(){
        return preferencesUser == null ? getSharedPreferences(USER_TABLE, 0).edit() : preferencesUser.edit();
    }

    public SharedPreferences getUserPreferences(){
        return preferencesUser == null ? getSharedPreferences(USER_TABLE, 0) : preferencesUser;
    }

    public SharedPreferences.Editor getQuestEditor(){
        return preferencesQuest == null ? getSharedPreferences(user.uiId + QUEST_TABLE, 0).edit() : preferencesQuest.edit();
    }

    public SharedPreferences getQuestPreferencesQuest(){
        return preferencesQuest == null ? getSharedPreferences(user.uiId + QUEST_TABLE, 0) : preferencesQuest;
    }

    public SharedPreferences.Editor getValueEditor(){
        return preferencesQuest == null ? getSharedPreferences(user.uiId + VALUE_TABLE, 0).edit() : preferencesValue.edit();
    }

    public SharedPreferences getQuestValuePreferencesQuest(){
        return preferencesQuest == null ? getSharedPreferences(user.uiId + VALUE_TABLE, 0) : preferencesValue;
    }
    
    public SharedPreferences.Editor getCompletenessEditor(){
        return preferencesCompleteness == null ? getSharedPreferences(user.uiId + COMPLETENESS, 0).edit()
                : preferencesCompleteness.edit();
    }

    public SharedPreferences getPreferencesCompleteness(){
        return preferencesCompleteness == null ? getSharedPreferences(user.uiId + COMPLETENESS, 0) : preferencesCompleteness;
    }

    public SharedPreferences.Editor getSignEditor(){
        return preferencesSign == null ? getSharedPreferences(user.uiId + SIGN, 0).edit()
                : preferencesSign.edit();
    }

    public SharedPreferences getPreferencesSign(){
        return preferencesSign == null ? getSharedPreferences(user.uiId + SIGN, 0) : preferencesSign;
    }

    public SharedPreferences.Editor getGroupIndexEditor(){
        return preferencesGroupIndex == null ? getSharedPreferences(user.uiId + GROUPINDEX, 0).edit()
                : preferencesGroupIndex.edit();
    }

    public SharedPreferences getPreferencesGroupIndex(){
        return preferencesGroupIndex == null ? getSharedPreferences(user.uiId + GROUPINDEX, 0) : preferencesGroupIndex;
    }
}
