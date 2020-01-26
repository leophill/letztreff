package com.lu.uni.letztreff;

import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

/* Used to obtain the application instance */

public class LetzTreffApplication extends Application {
    private static LetzTreffApplication instance;
    private static Context appContext;

    public static LetzTreffApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context mAppContext) {
        appContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}