package com.ityang.googlemarkrt.protocol;

import com.ityang.googlemarkrt.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/6.
 */
public class DetailProtocol extends BaseProtocol<AppInfo> {
    String packageName;
    public DetailProtocol(String packageName) {
        super();
        this.packageName = packageName;
    }
    @Override
    public AppInfo paserJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            long id = object.getLong("id");
            String name = object.getString("name");
            String packageName = object.getString("packageName");
            String iconUrl = object.getString("iconUrl");
            float stars = Float.parseFloat(object.getString("stars"));
            long size = object.getLong("size");
            String downloadUrl = object.getString("downloadUrl");
            String des = object.getString("des");
            String downloadNum = object.getString("downloadNum");
            String version = object.getString("version");
            String date = object.getString("date");
            String author = object.getString("author");
            List<String> screen = new ArrayList<String>();
            JSONArray screenArray = object.getJSONArray("screen");
            for (int i = 0; i < screenArray.length(); i++) {
                screen.add(screenArray.getString(i));
            }

            List<String> safeUrl = new ArrayList<String>();
            List<String> safeDesUrl = new ArrayList<String>();
            List<String> safeDes = new ArrayList<String>();
            List<Integer> safeDesColor = new ArrayList<Integer>();
            JSONArray jsonArray = object.getJSONArray("safe");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                safeUrl.add(jsonObject.getString("safeUrl"));
                safeDesUrl.add(jsonObject.getString("safeDesUrl"));
                safeDes.add(jsonObject.getString("safeDes"));
                safeDesColor.add(jsonObject.getInt("safeDesColor"));

            }
            AppInfo appInfo = new AppInfo(id, name, packageName, iconUrl,
                    stars, size, downloadUrl, des, downloadNum, version, date,
                    author, screen, safeUrl, safeDesUrl, safeDes, safeDesColor);
            return appInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParams() {
        return "&packageName=" + packageName;
    }

    @Override
    public String getFlag() {
        return "_"+packageName;
    }
}
