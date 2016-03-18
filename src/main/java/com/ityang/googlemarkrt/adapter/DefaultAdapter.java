package com.ityang.googlemarkrt.adapter;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ityang.googlemarkrt.holder.BaseHolder;
import com.ityang.googlemarkrt.holder.MoreHolder;
import com.ityang.googlemarkrt.manager.ThreadPoolManger;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.ityang.googlemarkrt.view.PullRefreshListView;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public abstract class DefaultAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {
    private List<T> datas;
    public List<T> getDatas() {
        return datas;
    }

    private static final int ITEM_DEFAULT = 0;
    private static final int ITEM_MORE = 1;
    private PullRefreshListView listView;
    public DefaultAdapter(List<T> datas,ListView listView) {
        this.datas = datas;
        this.listView = (PullRefreshListView) listView;
        listView.setOnItemClickListener(this);
    }

    @Override
    public int getCount() {
        return datas.size() + 1;
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
        if (position == datas.size()) {
            return ITEM_MORE;
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
        switch (getItemViewType(position)) {  // �жϵ�ǰ��Ŀʱʲô����
            case ITEM_MORE:
                if(convertView==null){
                    holder=getMoreHolder();
                }else{
                    holder=(BaseHolder) convertView.getTag();
                }
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
        return holder.getContentView();  //  �����ǰHolder ǡ����MoreHolder  ֤��MoreHOlder�Ѿ���ʾ
    }

    private MoreHolder holder;

    private BaseHolder getMoreHolder() {
        if (holder != null) {
            return holder;
        } else {
            holder = new MoreHolder(this);
            return holder;
        }
    }

    protected abstract BaseHolder getHolder();

    public void loadMore() {
        //�����̳߳�ִ�л�ȡ��������������
        ThreadPoolManger.getInstance().creatLongPool().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                final List<T> newDatas = loadMoreImpl();
                //�����߳��и�������
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newDatas == null) {
                            System.out.println("newDatas==null");
                            holder.showViews(MoreHolder.LOAD_ERROR);//
                        } else if (newDatas.size() == 0) {
                            holder.showViews(MoreHolder.HAS_NO_MORE);
                        } else {
                            // �ɹ���
                            holder.showViews(MoreHolder.HAS_MORE);
                            datas.addAll(newDatas);//  ��listView֮ǰ�ļ������һ���µļ���
                            notifyDataSetChanged();// ˢ�½���
                        }
                    }
                });

            }
        });
    }

    protected abstract List<T> loadMoreImpl();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //System.out.println("�����Ŀ");
            //����position

            int realPosition = position - listView.getHeaderViewsCount();
            onItemClickImpl(realPosition);


    }

    protected abstract void onItemClickImpl(int realposition);
}
