package com.ityang.googlemarkrt.protocol;

import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.domain.HomeTopImg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class HomeProtocol extends BaseProtocol<List<AppInfo>> {
    List<HomeTopImg> jsonPicArray = new ArrayList<HomeTopImg>();

    public List<HomeTopImg> getJsonPicArray() {
        return jsonPicArray;
    }

    @Override
    public List<AppInfo> paserJson(String json) {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            //Ö÷Ò³¶¥²¿Í¼Æ¬url
            JSONArray jsonArray2 = jsonObject.getJSONArray("picture");
            for(int i=0;i<jsonArray2.length();i++){
                JSONObject jsonObj = jsonArray2.getJSONObject(i);
                String imgurl = jsonObj.getString("imgurl");
                String imgdes = jsonObj.getString("imgdes");
                HomeTopImg homeTopImg = new HomeTopImg(imgurl,imgdes);
                jsonPicArray.add(homeTopImg);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                long id = jsonObj.getLong("id");
                String name = jsonObj.getString("name");
                String packageName = jsonObj.getString("packageName");
                String iconUrl = jsonObj.getString("iconUrl");
                float stars = Float.parseFloat(jsonObj.getString("stars"));
                long size = jsonObj.getLong("size");
                String downloadUrl = jsonObj.getString("downloadUrl");
                String des = jsonObj.getString("des");
                AppInfo info = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                appInfos.add(info);
            }
            return appInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "home";
    }


}
