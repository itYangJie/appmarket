package com.ityang.googlemarkrt.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.adapter.DefaultAdapter;
import com.ityang.googlemarkrt.domain.SubjectInfo;
import com.ityang.googlemarkrt.holder.BaseHolder;
import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.protocol.SubjectProtocol;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.ityang.googlemarkrt.view.PullRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends BaseFragment {

    private List<SubjectInfo> subjects =  new ArrayList<SubjectInfo>();
    private SubjectAdapter adapter = null;

    @Override
    public View createSuccessView() {
        PullRefreshListView listView = new PullRefreshListView<SubjectInfo>(UiUtils.getContext()){
            @Override
            protected List<SubjectInfo> pullRefresh() {
                SubjectProtocol protocol = new SubjectProtocol();
                List<SubjectInfo> refreshSubInfos = protocol.load(0);
                return refreshSubInfos;
            }
            @Override
            protected void onRefreshSuccess(List<SubjectInfo> refreshDatas) {
                subjects.clear();
                subjects.addAll(refreshDatas);
                adapter.notifyDataSetChanged();
            }
        };
        adapter = new SubjectAdapter(subjects,listView);
        listView.setAdapter(adapter);
        return listView;
    }

    private class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> datas, ListView listView) {
            super(datas, listView);
        }

        @Override
        protected BaseHolder getHolder() {
            return new SubjectHolder();
        }

        @Override
        protected List<SubjectInfo> loadMoreImpl() {
            SubjectProtocol protocol = new SubjectProtocol();
            List<SubjectInfo> load = protocol.load(subjects.size());
            if(load!=null && load.size()>0) {
                subjects.addAll(load);
            }
            return load;
        }

        @Override
        protected void onItemClickImpl(int realposition) {

        }
    }
    class SubjectHolder extends BaseHolder<SubjectInfo> {
        ImageView item_icon;
        TextView item_txt;
        @Override
        protected View initViews() {
            View view = View.inflate(UiUtils.getContext(), R.layout.item_subject, null);
            this.item_icon = (ImageView) view.findViewById(R.id.item_icon);
            this.item_txt = (TextView) view.findViewById(R.id.item_txt);
            return view;
        }
        @Override
        protected void refreshViews(SubjectInfo data) {
            this.item_txt.setText(data.getDes());
            bitmapUtils.display(this.item_icon, HttpHelper.URL + "image?name=" + data.getUrl());
        }
    }

    @Override
    protected int getDataFromServer() {
        SubjectProtocol protocol = new SubjectProtocol();
        subjects = protocol.load(subjects.size());
        return checkData(subjects);
    }
}
