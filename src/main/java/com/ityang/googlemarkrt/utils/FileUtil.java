package com.ityang.googlemarkrt.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015/9/20.
 */
public class FileUtil {
    public static final String CACHE = "cache";
    public static final String ICON = "icon";
    public static final String ROOT = "GoogleMarket";

    private FileUtil(){

    }
    private static File getDir(String dirName){
        StringBuilder builder = new StringBuilder();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //有sd卡
            String basePath =Environment.getExternalStorageDirectory().getAbsolutePath();
            builder.append(basePath);
            builder.append(File.separator);// '/'
            builder.append(ROOT);// /mnt/sdcard/GooglePlay
            builder.append(File.separator);
            builder.append(dirName);// /mnt/sdcard/GooglePlay/cache
        }else {
            //无sd卡
            File filesDir = UiUtils.getContext().getCacheDir();    //  cache  getFileDir file
            builder.append(filesDir.getAbsolutePath());// /data/data/com.itheima.googleplay/cache
            builder.append(File.separator);///data/data/com.itheima.googleplay/cache/
            builder.append(dirName);///data/data/com.itheima.googleplay/cache/cache
        }
        //文件夹路径名
        String path = builder.toString();
        File file = new File(path);
        //不存在该路径或者不是文件夹，则创建文件夹
        if((!file.exists())||(!file.isDirectory())){
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取图片的缓存的路径
     * @return
     */
    public static File getIconDir(){
        return getDir(ICON);

    }
    /**
     *
     * 返回缓存文件夹
     * @return
     */
    public static File getCacheDir(){
        return getDir(CACHE);
    }
}
