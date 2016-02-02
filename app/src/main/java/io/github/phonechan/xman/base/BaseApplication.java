package io.github.phonechan.xman.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by apple on 16/2/1.
 */
public class BaseApplication extends Application {

    static Context appContext;
    static Resources appResources;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        appResources = appContext.getResources();
    }

    public static synchronized BaseApplication appContext() {
        return (BaseApplication) appContext;
    }

    public static Resources appResources() {
        return appResources;
    }
}
