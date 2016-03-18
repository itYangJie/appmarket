package com.ityang.googlemarkrt.fragment;

import android.view.View;

import com.ityang.googlemarkrt.adapter.CategoryAdapter;
import com.ityang.googlemarkrt.domain.CategoryInfo;

import java.util.List;

public class CategoryFragment extends BaseFragment {
    private List<CategoryInfo> datas;
    private CategoryAdapter adapter;

    @Override
    protected View createSuccessView() {
       /* BaseListView listView = new BaseListView(UiUtils.getContext());
		adapter = new CategoryAdapter(datas) {
			@Override
			protected BaseHolder getHolder() {
				return new CategoryItemHolder();
			}
		};
		listView.setAdapter(adapter);
		return listView;*/
        return null;
    }

    @Override
    protected int getDataFromServer() {
      /*  CategoryProtocol protocol = new CategoryProtocol();
        datas = protocol.load(0);

        return checkData(datas);*/
       return STATE_ERROR;
    }
}
