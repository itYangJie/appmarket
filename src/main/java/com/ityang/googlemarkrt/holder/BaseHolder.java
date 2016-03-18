package com.ityang.googlemarkrt.holder;

import android.view.View;

import com.ityang.googlemarkrt.utils.BitmapUtilHelper;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/9/24.
 */
public abstract class BaseHolder<T> {
    protected View contentView;
    protected BitmapUtils bitmapUtils;

    private T data;
    public View getContentView() {
        return this.contentView;
    }
    public BaseHolder(){
        bitmapUtils = BitmapUtilHelper.getBitmapUtils();
        contentView=initViews();
        contentView.setTag(this);
    }

    public void showViews(T data){
        this.data = data;
        refreshViews(data);
    }
    protected abstract View initViews() ;


    protected abstract void refreshViews(T data);
}
