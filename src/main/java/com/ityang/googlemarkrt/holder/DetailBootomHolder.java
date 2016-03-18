package com.ityang.googlemarkrt.holder;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.utils.UiUtils;

/**
 * Created by Administrator on 2015/10/14.
 */
public class DetailBootomHolder extends BaseHolder<AppInfo> implements View.OnClickListener {

    private Button bottom_favorites;
    private Button progress_btn;
    private Button bottom_share;
    private AppInfo data;
    @Override
    protected View initViews() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_bottom,null);
        bottom_favorites = (Button) view.findViewById(R.id.bottom_favorites);
        progress_btn = (Button) view.findViewById(R.id.progress_btn);
        bottom_share = (Button) view.findViewById(R.id.bottom_share);

        bottom_favorites.setOnClickListener(this);
        progress_btn.setOnClickListener(this);
        bottom_share.setOnClickListener(this);
        return view;
    }

    @Override
    protected void refreshViews(AppInfo data) {
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_favorites:
                Toast.makeText(UiUtils.getContext()," ’≤ÿ"+data.getName(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_btn:
                Toast.makeText(UiUtils.getContext(),"œ¬‘ÿ"+data.getName(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_share:
                Toast.makeText(UiUtils.getContext(),"∑÷œÌ"+data.getName(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
