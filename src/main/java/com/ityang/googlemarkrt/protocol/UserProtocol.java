package com.ityang.googlemarkrt.protocol;

import com.ityang.googlemarkrt.domain.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProtocol extends BaseProtocol<UserInfo> {

	@Override
	public UserInfo paserJson(String json) {
		try {
			JSONObject jsonObject=new JSONObject(json);
			String name=jsonObject.getString("name");
			String email=jsonObject.getString("email");
			String url=jsonObject.getString("url");
			UserInfo userInfo=new UserInfo(name, url, email);
			return userInfo;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getKey() {
		return "user";
	}

}
