package com.ityang.googlemarkrt.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DetailInfoHolder extends BaseHolder<AppInfo> {

	@ViewInject(R.id.item_icon)
	private ImageView item_icon;
	@ViewInject(R.id.item_title)
	private TextView item_title;
	@ViewInject(R.id.item_rating)
	private RatingBar item_rating;
	@ViewInject(R.id.item_download)
	private TextView item_download;
	@ViewInject(R.id.item_version)
	private TextView item_version;
	@ViewInject(R.id.item_date)
	private TextView item_date;
	@ViewInject(R.id.item_size)
	private TextView item_size;
	/***
	 * ʵ�����ؼ��Ͳ���
	 */
	@Override
	public View initViews() {
		View view =View.inflate(UiUtils.getContext(),R.layout.detail_app_info,null);
		ViewUtils.inject(this, view);
		return view;
	}
	/**
	 * ���ؼ���������
	 */
	@Override
	public void refreshViews(AppInfo data) {
		bitmapUtils.display(item_icon, HttpHelper.URL+"image?name="+data.getIconUrl());
		item_title.setText(data.getName());
		item_rating.setRating(data.getStars());
		item_download.setText("����:"+data.getDownloadNum());
		item_version.setText("�汾:"+data.getVersion());
		item_date.setText("ʱ��:"+data.getDate());
		item_size.setText("��С:"+Formatter.formatFileSize(UiUtils.getContext(), data.getSize()));
	}

}
