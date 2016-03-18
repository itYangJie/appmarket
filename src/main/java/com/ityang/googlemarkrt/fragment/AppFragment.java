package com.ityang.googlemarkrt.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.ityang.googlemarkrt.DetailActivity;
import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.adapter.DefaultAdapter;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.holder.BaseHolder;
import com.ityang.googlemarkrt.holder.BaseListHolder;
import com.ityang.googlemarkrt.protocol.AppProtocol;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.ityang.googlemarkrt.view.PullRefreshListView;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class AppFragment extends BaseFragment {
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();
    private AppAdapter adapter;

    @Override
    protected View createSuccessView() {
        PullRefreshListView listView = new PullRefreshListView<AppInfo>(UiUtils.getContext()){
            @Override
            protected List<AppInfo> pullRefresh() {
                AppProtocol protocol = new AppProtocol();
                List<AppInfo> refreshAppInfos = protocol.load(0);
                return refreshAppInfos;
            }
            @Override
            protected void onRefreshSuccess(List<AppInfo> refreshDatas) {
                appInfos.clear();
                appInfos.addAll(refreshDatas);
                adapter.notifyDataSetChanged();
            }
        };
        adapter = new AppAdapter(appInfos,listView);
        listView.setAdapter(adapter);

        // 第二个参数 慢慢滑动的时候是否加载图片 false  加载   true 不加载
        //  第三个参数  飞速滑动的时候是否加载图片  true 不加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        //设置加载中和加载错误是显示的图片
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        return listView;
    }

    @Override
    protected int getDataFromServer() {
        AppProtocol protocol = new AppProtocol();
        appInfos = protocol.load(0);
        return checkData(appInfos);
    }

    class AppAdapter extends DefaultAdapter<AppInfo> {

        public AppAdapter(List<AppInfo> datas, ListView listView) {
            super(datas, listView);
        }
        @Override
        protected BaseHolder getHolder() {
            return new BaseListHolder();
        }
        @Override
        protected List<AppInfo> loadMoreImpl() {
            AppProtocol protocol = new AppProtocol();
            List<AppInfo> load = protocol.load(appInfos.size());
            if(load!=null && load.size()>0) {
                appInfos.addAll(load);
            }
            return load;
        }
        @Override
        protected void onItemClickImpl(int realPosition) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("packagename",appInfos.get(realPosition).getPackageName());
            startActivity(intent);
        }
    }
}
