package com.ityang.googlemarkrt.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ityang.googlemarkrt.domain.CategoryInfo;
import com.ityang.googlemarkrt.utils.UiUtils;

/**
 * Created by Administrator on 2015/10/17.
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {
    private TextView tv;
    @Override
    protected View initViews() {
        tv= new TextView(UiUtils.getContext());
        tv.setPadding(UiUtils.dip2px(5),UiUtils.dip2px(5),UiUtils.dip2px(5),UiUtils.dip2px(5));
        tv.setTextSize(UiUtils.dip2px(25));
        tv.setTextColor(Color.BLUE);
        return tv;
    }

    @Override
    protected void refreshViews(CategoryInfo data) {
        tv.setText(data.getTitle());
    }
}
