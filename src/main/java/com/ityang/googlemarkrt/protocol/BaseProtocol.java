package com.ityang.googlemarkrt.protocol;

import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.utils.FileUtil;
import com.lidroid.xutils.util.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Administrator on 2015/9/20.
 */
public abstract class BaseProtocol<T> {

    public T load(int index) {
        // 加载本地数据
        String json = loadDatasFromLocal(index);
        if (json == null) {
            //System.out.println("本地数据没有");
            // 请求服务器
            json = loadDatasFromNet(index);
            if (json != null) {
                //System.out.println("网络数据有");
                saveLocal(json, index);
            } else {
               // System.out.println("网络数据没有");
            }
        } else {
           // System.out.println("本地数据有");
        }
        T data;
        if (json != null) {
             data  = paserJson(json);
        } else {
            return null;
        }
        return data;
       // return paserJson(json);
    }

    //1.从本地读取

    /**
     * 解析json
     *
     * @param json
     * @return
     */
    public abstract T paserJson(String json);

    public abstract String getKey();

    /**
     * 从网络中获取的数据保存到本地
     *
     * @param netDatas
     * @param index
     */
    private void saveLocal(String netDatas, int index) {
        BufferedWriter bufferedWriter = null;
        try {
            //缓存路径文件对象
            File file = new File(FileUtil.getCacheDir(), "/" + getKey() + getFlag()+"_" + index);
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            //保存过期时间 在当前时间基础加上100秒
            bufferedWriter.write(System.currentTimeMillis() + 1000 * 100 + "");
            //将数据保存在新的一行
            bufferedWriter.newLine();
            bufferedWriter.write(netDatas);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }

    public String getFlag() {
        return "";
    }

    private String loadDatasFromLocal(int index) {
        try {
            File file = new File(FileUtil.getCacheDir(), "/" + getKey() +getFlag()+ "_" + index);
            //文件不存在
            if (!file.exists()) {
                return null;
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if (System.currentTimeMillis() >= Long.parseLong(bufferedReader.readLine())) {
                //时间过期了需要重新从网络获取
                return null;
            }
            StringWriter stringWriter = new StringWriter();
            String buffer = null;
            while ((buffer = bufferedReader.readLine()) != null) {
                stringWriter.write(buffer);
            }
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadDatasFromNet(int index) {
        String json = null;
        try {
            HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index+getParams());
            json = httpResult.getString();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getParams() {
        return "";
    }

}
