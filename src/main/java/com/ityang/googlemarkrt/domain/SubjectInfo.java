package com.ityang.googlemarkrt.domain;

public class SubjectInfo {
	private String des;
	private String url;
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "SubjectInfo [des=" + des + ", url=" + url + "]";
	}
	public SubjectInfo() {
		super();
	}
	public SubjectInfo(String des, String url) {
		super();
		this.des = des;
		this.url = url;
	}
}
