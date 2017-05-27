package com.campus.android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;

/**
 * Created by lebron on 17-5-27.
 */

public class MainApplication extends Application {
    private static Application sAppContext;

    public static Application getAppContext() {
        return sAppContext;
    }

    public MainApplication() {
        sAppContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Bmob.initialize(this, "0f7fec08ede8776597e3db8a9ee132dc");
        sAppContext = this;
    }
}
