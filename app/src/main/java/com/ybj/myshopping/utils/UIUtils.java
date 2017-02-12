package com.ybj.myshopping.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.ybj.myshopping.common.MyApplication;

/**
 * Created by 杨阳洋 on 2017/2/10.
 * usg:UI操作
 */

public class UIUtils {
    /**
     * 获取程序需要的上下文对象：返回的是MyApplication的对象
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.mContext;
    }

    /**
     * 获取程序需要的消息处理器对象
     *
     * @return
     */
    public static Handler getHandler() {
        return MyApplication.mHandler;
    }

    /**
     * 返回指定ID对应的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    /**
     * 返回指定布局id所对应的视图对象
     *
     * @param layouId
     * @return
     */
    public static View getView(int layouId) {
        return View.inflate(getContext(), layouId, null);
    }

    /**
     * sp转px的方法。
     */
    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp--->px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        // 获取手机的密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    /**
     * px--->dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        // 获取手机的密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    /**
     * 获取指定id的字符串数组
     *
     * @param strArryId
     * @return
     */
    public static String[] getStrArray(int strArryId) {
        return getContext().getResources().getStringArray(strArryId);
    }

    /**
     * 保证在主线程中操作
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {

        if (isMainThread()) {
            runnable.run();
        } else {
            //post（）类似于sendMessage()方法
            UIUtils.getHandler().post(runnable);
        }

    }

    /**
     * 判断是否在主线程
     *
     * @return
     */
    private static boolean isMainThread() {

        int currendThreadId = Process.myTid();

        return MyApplication.mainThreadId == currendThreadId;
    }
}
