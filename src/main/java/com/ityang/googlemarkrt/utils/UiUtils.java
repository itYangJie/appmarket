package com.ityang.googlemarkrt.utils;

import android.content.Context;
import android.content.res.Resources;

import com.ityang.googlemarkrt.BaseApplication;

public class UiUtils {

    private UiUtils(){

    }
    /**
     * 获取到字符数组
     *
     * @param tabNames 字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    public static Resources getResource() {
        return BaseApplication.getApplication().getResources();
    }
    public static Context getContext(){
        return BaseApplication.getApplication();
    }
    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
    /**
     * 把Runnable 方法提交到主线程运行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if(android.os.Process.myTid()==BaseApplication.getMainTid()){
            runnable.run();
        }else{
            //获取handler
            BaseApplication.getHandler().post(runnable);
        }
    }
    /**
     * pxz转换dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
