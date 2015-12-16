package com.nice.httpapi.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.GregorianCalendar;

/**
 * Created by i068020 on 2/28/15.
 */
public class QSGsonFactory {

    public static Gson create() {
        return createBuilder().create();
    }
//
//    public static Gson create(Class omit){
//        return createDeserializerBuilder(TypeToken.get(omit)).create();
//    }

    public static GsonBuilder createBuilder(){
        GsonBuilder builder = new GsonBuilder();
        return builder;
    }
//
//    private static GsonBuilder createDeserializerBuilder(TypeToken omit) {
//        GsonBuilder builder = new GsonBuilder();
//        return builder.registerTypeAdapterFactory(new OmitTypeAdpaterFactory(omit, createBuilder()));
//    }
}
