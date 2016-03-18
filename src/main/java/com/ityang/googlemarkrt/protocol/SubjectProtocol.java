package com.ityang.googlemarkrt.protocol;

import com.ityang.googlemarkrt.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {

    @Override
    public List<SubjectInfo> paserJson(String json) {
        List<SubjectInfo> subjectInfos=new ArrayList<SubjectInfo>();
        try {
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String des=jsonObject.getString("des");
                String url = jsonObject.getString("url");
                SubjectInfo info=new SubjectInfo(des, url);
                subjectInfos.add(info);
            }
            return subjectInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "subject";
    }
}
