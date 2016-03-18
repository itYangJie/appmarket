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
        // ���ر�������
        String json = loadDatasFromLocal(index);
        if (json == null) {
            //System.out.println("��������û��");
            // ���������
            json = loadDatasFromNet(index);
            if (json != null) {
                //System.out.println("����������");
                saveLocal(json, index);
            } else {
               // System.out.println("��������û��");
            }
        } else {
           // System.out.println("����������");
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

    //1.�ӱ��ض�ȡ

    /**
     * ����json
     *
     * @param json
     * @return
     */
    public abstract T paserJson(String json);

    public abstract String getKey();

    /**
     * �������л�ȡ�����ݱ��浽����
     *
     * @param netDatas
     * @param index
     */
    private void saveLocal(String netDatas, int index) {
        BufferedWriter bufferedWriter = null;
        try {
            //����·���ļ�����
            File file = new File(FileUtil.getCacheDir(), "/" + getKey() + getFlag()+"_" + index);
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            //�������ʱ�� �ڵ�ǰʱ���������100��
            bufferedWriter.write(System.currentTimeMillis() + 1000 * 100 + "");
            //�����ݱ������µ�һ��
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
            //�ļ�������
            if (!file.exists()) {
                return null;
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if (System.currentTimeMillis() >= Long.parseLong(bufferedReader.readLine())) {
                //ʱ���������Ҫ���´������ȡ
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
