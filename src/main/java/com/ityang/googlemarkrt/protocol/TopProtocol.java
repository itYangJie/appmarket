package com.ityang.googlemarkrt.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/22.
 */
public class TopProtocol extends BaseProtocol<List<String>> {

    @Override
    public List<String> paserJson(String json) {
        List<String> tops = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String str = jsonArray.getString(i);
                tops.add(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tops;
    }

    @Override
    public String getKey() {
        return "hot";
    }
}
