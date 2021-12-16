package com.blackorangejuice.songguojizhang.utils.other;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * 全局获取context，并初始化litepal框架
 */
public class SelfApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
