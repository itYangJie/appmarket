package com.ityang.googlemarkrt.holder;

import android.view.View;
import android.widget.ImageView;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.utils.UiUtils;

import java.util.List;

public class DetailScreenHolder extends BaseHolder<AppInfo> {
	private ImageView[] ivs;
	@Override
	public View initViews() {
		View view= View.inflate(UiUtils.getContext(),R.layout.detail_screen,null);
		ivs=new ImageView[5];
		ivs[0]=(ImageView) view.findViewById(R.id.screen_1);
		ivs[1]=(ImageView) view.findViewById(R.id.screen_2);
		ivs[2]=(ImageView) view.findViewById(R.id.screen_3);
		ivs[3]=(ImageView) view.findViewById(R.id.screen_4);
		ivs[4]=(ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	public void refreshViews(AppInfo data) {
		List<String> screen = data.getScreen(); // 集合的大小有可能小于5 
		for(int i=0;i<5;i++){
			if(i<screen.size()){
				ivs[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(ivs[i], HttpHelper.URL+"image?name="+screen.get(i));
			}else{
				ivs[i].setVisibility(View.GONE);
			}
		}
	}

}
