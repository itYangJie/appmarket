package com.ityang.googlemarkrt.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.protocol.TopProtocol;
import com.ityang.googlemarkrt.utils.DrawableUtil;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.ityang.googlemarkrt.view.TagLayout2;

import java.util.List;
import java.util.Random;

public class TopFragment extends BaseFragment {

	private List<String> datas = null;

	@Override
	protected View createSuccessView() {
		ScrollView scrollView = new ScrollView(UiUtils.getContext());

		TagLayout2 layout=new TagLayout2(UiUtils.getContext());
		int padding=UiUtils.dip2px(13);
		layout.setPadding(padding, padding, padding, padding);

		//���ñ���
		layout.setBackgroundResource(R.drawable.list_item_bg);
		Drawable pressedDrawable = DrawableUtil.getShape(0xffcecece);

		//�������ݼ��ϴ���textview����linearoutlayout
		for(final String str:datas){
			TextView textView = new TextView(UiUtils.getContext());
			//���ò���
			textView.setText(str);
			textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(UiUtils.dip2px(8));
			textView.setPadding(UiUtils.dip2px(7),UiUtils.dip2px(4),UiUtils.dip2px(7),UiUtils.dip2px(4));
			//�������������ɫ
			Random random=new Random();   //�������
			int red = random.nextInt(200)+22;
			int green = random.nextInt(200)+22;
			int blue = random.nextInt(200)+22;
			int color=Color.rgb(red, green, blue);//��Χ 0-255
			Drawable normalDrawable = DrawableUtil.getShape(color);
			//�������ֱ���drawable
			textView.setBackgroundDrawable(DrawableUtil.getStateDrawable(pressedDrawable, normalDrawable));
			textView.setClickable(true);
			//����¼�
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(UiUtils.getContext(),str,Toast.LENGTH_SHORT).show();
				}
			});
			layout.addView(textView);
		}
		scrollView.addView(layout);
		return scrollView;
	}

	@Override
	protected int getDataFromServer() {
		TopProtocol protocol = new TopProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}
}
