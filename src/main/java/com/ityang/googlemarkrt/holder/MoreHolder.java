package com.ityang.googlemarkrt.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ityang.googlemarkrt.BaseApplication;
import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.adapter.DefaultAdapter;
import com.ityang.googlemarkrt.utils.UiUtils;

/**
 * Created by Administrator on 2015/9/28.
 */
public class MoreHolder extends BaseHolder<Integer> {
    public static final int HAS_NO_MORE = 0;  // 没有额外数据了
    public static final int LOAD_ERROR = 1;// 加载失败
    public static final int HAS_MORE = 2;//  有额外数据

    private RelativeLayout rl_more_loading, rl_more_error;
    private DefaultAdapter adapter;

    public MoreHolder(DefaultAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    @Override
    protected View initViews() {
        View view = View.inflate(BaseApplication.getApplication(), R.layout.load_more, null);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }

    /**
     * 覆盖父类方法，从服务器获取更多数据
     */
    @Override
    public View getContentView() {
        loadMore();
        return super.getContentView();
    }

    private void loadMore() {
        //  交给Adapter  让Adapter  加载更多数据
        adapter.loadMore();
    }

    @Override
    protected void refreshViews(Integer data) {
        rl_more_error.setVisibility(data == LOAD_ERROR ? View.VISIBLE : View.GONE);
        rl_more_loading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
        //没有数据了，则提示用户
        if(data!=LOAD_ERROR && data!=HAS_MORE){
            UiUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UiUtils.getContext(),"数据都被你看完了，没有更多了",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
