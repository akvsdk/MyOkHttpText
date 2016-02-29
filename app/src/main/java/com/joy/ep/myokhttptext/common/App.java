package com.joy.ep.myokhttptext.common;

import android.app.Application;
import android.content.Context;

import com.joy.ep.myokhttptext.BuildConfig;
import com.socks.library.KLog;

/**
 * author   Joy
 * Date:  2016/2/14.
 * version:  V1.0
 * Description:
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.LOG_DEBUG);
    }

    public static Context getContext() {
        return context;
    }

}
