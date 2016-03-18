package com.ityang.googlemarkrt.domain;

/**
 * Created by Administrator on 2015/10/1.
 */
public class HomeTopImg {
    private String imgurl;

    public String getImgdes() {
        return imgdes;
    }

    public void setImgdes(String imgdes) {
        this.imgdes = imgdes;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public HomeTopImg(String imgurl, String imgdes) {
        this.imgurl = imgurl;
        this.imgdes = imgdes;
    }

    private String imgdes;
}
