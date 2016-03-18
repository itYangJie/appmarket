package com.ityang.googlemarkrt.utils;

import android.content.Context;
import android.content.res.Resources;

import com.ityang.googlemarkrt.BaseApplication;

public class UiUtils {

    private UiUtils(){

    }
    /**
     * ��ȡ���ַ�����
     *
     * @param tabNames �ַ������id
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
     * dipת��px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
    /**
     * ��Runnable �����ύ�����߳�����
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // �����߳�����
        if(android.os.Process.myTid()==BaseApplication.getMainTid()){
            runnable.run();
        }else{
            //��ȡhandler
            BaseApplication.getHandler().post(runnable);
        }
    }
    /**
     * pxzת��dip
     */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}