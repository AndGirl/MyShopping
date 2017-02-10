package com.ybj.myshopping.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by 杨阳洋 on 2017/2/10.
 * usg:初始化操作
 */

public class MyApplication extends Application{
    public static Context mContext;
    public static Handler mHandler;
    public static Thread mainThread; // 获取主线程
    public static int mainThreadId;//线程ID

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
        mHandler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();// 获取当前主线程的ID
    }

}
