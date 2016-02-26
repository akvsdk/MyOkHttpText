package com.joy.ep.myokhttptext.base;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return context;
    }

}
