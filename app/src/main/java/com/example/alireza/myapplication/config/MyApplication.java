package com.example.alireza.myapplication.config;

import android.app.Application;
import android.util.Log;

import  com.activeandroid.ActiveAndroid;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by alireza on 3/22/2018.
 */

public class MyApplication  extends Application {
    private static MyApplication sInstance;
    private Gson gson;
    @Override
    public void onCreate() {
        super.onCreate();
        // build new gson instance
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();

        sInstance = this;
        ActiveAndroid.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/IRANSansMobile.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build()
        );
    }


    /**
     * Get global gson instance
     * @return
     */
    public Gson getGson(){
        return gson;
    }

    /**
     * Restrict access to the app through a static instance
     * @return
     */
    public static MyApplication getApp(){
        return sInstance;
    }
}
