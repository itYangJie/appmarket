package com.ityang.googlemarkrt.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/9/22.
 */
public class BitmapUtilHelper {
    private static BitmapUtils bitmapUtils;
    private BitmapUtilHelper(){

    }
    public static BitmapUtils getBitmapUtils(){
        if(bitmapUtils==null){
            bitmapUtils = new BitmapUtils(UiUtils.getContext(),FileUtil.getIconDir().getAbsolutePath(),0.3f);
        }
        return bitmapUtils;
    }
}
