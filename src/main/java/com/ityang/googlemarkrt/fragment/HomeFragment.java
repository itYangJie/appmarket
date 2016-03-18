package com.ityang.googlemarkrt.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.ityang.googlemarkrt.DetailActivity;
import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.adapter.DefaultAdapter;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.domain.HomeTopImg;
import com.ityang.googlemarkrt.holder.BaseHolder;
import com.ityang.googlemarkrt.holder.BaseListHolder;
import com.ityang.googlemarkrt.holder.HomeTopPicHolder;
import com.ityang.googlemarkrt.protocol.HomeProtocol;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.ityang.googlemarkrt.view.PullRefreshListView;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();
    private List<HomeTopImg> pictures; //  顶部ViewPager 显示界面的数据
    private HomeAdapter adapter;

    /* 创建加载成功界面 */
    public View createSuccessView() {
        //System.out.println("createSuccessView");
        PullRefreshListView listView = new PullRefreshListView<AppInfo>(UiUtils.getContext()){
            @Override
            protected List<AppInfo> pullRefresh() {
                HomeProtocol protocol = new HomeProtocol();
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
        adapter=new HomeAdapter(appInfos,listView);
        listView.setAdapter(adapter);

        HomeTopPicHolder homeTopPicHolder = new HomeTopPicHolder();
        homeTopPicHolder.showViews(pictures);
        listView.addHeaderView(homeTopPicHolder.getContentView());

        // 第二个参数 慢慢滑动的时候是否加载图片 false  加载   true 不加载
        //  第三个参数  飞速滑动的时候是否加载图片  true 不加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        //设置加载中和加载错误是显示的图片
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        return listView;
    }

    class HomeAdapter extends DefaultAdapter<AppInfo> {
        public HomeAdapter(List<AppInfo> datas, ListView listView) {
            super(datas, listView);
        }
        @Override
        protected BaseHolder getHolder() {
            return new BaseListHolder();
        }
        @Override
        protected List<AppInfo> loadMoreImpl() {
            HomeProtocol protocol = new HomeProtocol();
            List<AppInfo> load = protocol.load(appInfos.size());
          /*  if(load!=null && load.size()>0) {
                appInfos.addAll(load);
            }*/
            return load;
        }
        @Override
        protected void onItemClickImpl(int realPosition) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("packagename",appInfos.get(realPosition).getPackageName());
            startActivity(intent);
        }
    }
    /**
     * 访问服务器
     */
    public int getDataFromServer() {
        HomeProtocol protocol = new HomeProtocol();
        appInfos = protocol.load(0);
        pictures = protocol.getJsonPicArray();
        return checkData(appInfos);
    }

}
