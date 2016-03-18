package com.ityang.googlemarkrt.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ityang.googlemarkrt.domain.CategoryInfo;
import com.ityang.googlemarkrt.holder.BaseHolder;
import com.ityang.googlemarkrt.holder.CategoryTitleHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public abstract class CategoryAdapter extends BaseAdapter {
    private List<CategoryInfo> datas;

    private static final int ITEM_DEFAULT = 0;
    private static final int ITEM_TITLE = 1;

    public CategoryAdapter(List<CategoryInfo> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).isTitle()) {
            return ITEM_TITLE;
        } else {
            return ITEM_DEFAULT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        switch (getItemViewType(position)) {  // 判断当前条目时什么类型
            case ITEM_TITLE:
                if (convertView == null) {
                    holder = getTitleHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }
                holder.showViews(datas.get(position));
                break;
            case ITEM_DEFAULT:
                if (convertView == null) {
                    holder = getHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }
                if (position < datas.size()) {
                    holder.showViews(datas.get(position));
                }
                break;
        }
        return holder.getContentView();
    }

    private BaseHolder getTitleHolder() {
        return new CategoryTitleHolder();
    }

    protected abstract BaseHolder getHolder();
}
