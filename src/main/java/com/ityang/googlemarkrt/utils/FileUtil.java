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
            //��sd��
            String basePath =Environment.getExternalStorageDirectory().getAbsolutePath();
            builder.append(basePath);
            builder.append(File.separator);// '/'
            builder.append(ROOT);// /mnt/sdcard/GooglePlay
            builder.append(File.separator);
            builder.append(dirName);// /mnt/sdcard/GooglePlay/cache
        }else {
            //��sd��
            File filesDir = UiUtils.getContext().getCacheDir();    //  cache  getFileDir file
            builder.append(filesDir.getAbsolutePath());// /data/data/com.itheima.googleplay/cache
            builder.append(File.separator);///data/data/com.itheima.googleplay/cache/
            builder.append(dirName);///data/data/com.itheima.googleplay/cache/cache
        }
        //�ļ���·����
        String path = builder.toString();
        File file = new File(path);
        //�����ڸ�·�����߲����ļ��У��򴴽��ļ���
        if((!file.exists())||(!file.isDirectory())){
            file.mkdirs();
        }
        return file;
    }

    /**
     * ��ȡͼƬ�Ļ����·��
     * @return
     */
    public static File getIconDir(){
        return getDir(ICON);

    }
    /**
     *
     * ���ػ����ļ���
     * @return
     */
    public static File getCacheDir(){
        return getDir(CACHE);
    }
}
