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
import com.ityang.googlemarkrt.protocol.GameProtocol;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.ityang.googlemarkrt.view.PullRefreshListView;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends BaseFragment {
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();

    private GameAdapter adapter;

    @Override
    protected View createSuccessView() {
        PullRefreshListView listView = new PullRefreshListView<AppInfo>(UiUtils.getContext()){
            @Override
            protected List<AppInfo> pullRefresh() {
                GameProtocol protocol = new GameProtocol();
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
        adapter = new GameAdapter(appInfos,listView);
        listView.setAdapter(adapter);

        // �ڶ������� ����������ʱ���Ƿ����ͼƬ false  ����   true ������
        //  ����������  ���ٻ�����ʱ���Ƿ����ͼƬ  true ������
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));
        //���ü����кͼ��ش�������ʾ��ͼƬ
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        return listView;
    }

    class GameAdapter extends DefaultAdapter<AppInfo> {

        public GameAdapter(List<AppInfo> datas, ListView listView) {
            super(datas, listView);
        }
        @Override
        protected BaseHolder getHolder() {
            return new BaseListHolder();
        }

        @Override
        protected List<AppInfo> loadMoreImpl() {
            GameProtocol protocol = new GameProtocol();
            List<AppInfo> load = protocol.load(appInfos.size());
            if(load!=null && load.size()>0) {
                appInfos.addAll(load);
            }
            return load;
        }

        @Override
        protected void onItemClickImpl(int realposition) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("packagename",appInfos.get(realposition).getPackageName());
            startActivity(intent);
        }
    }

    @Override
    protected int getDataFromServer() {
        GameProtocol protocol = new GameProtocol();
        appInfos = protocol.load(0);
        return checkData(appInfos);
    }
}
