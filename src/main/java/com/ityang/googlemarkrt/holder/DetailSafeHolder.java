package com.ityang.googlemarkrt.holder;

import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import java.util.List;

public class DetailSafeHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	@ViewInject(R.id.safe_layout)
	private RelativeLayout safe_layout;
	@ViewInject(R.id.safe_content)
	private LinearLayout safe_content;
	@ViewInject(R.id.safe_arrow)
	private ImageView safe_arrow;
	ImageView[] ivs;
	ImageView[] iv_des;
	TextView[] tv_des;
	LinearLayout[] des_layout;


	@Override
	public View initViews() {

		View view =View.inflate(UiUtils.getContext(),R.layout.detail_safe,null);
		ViewUtils.inject(this, view);

		ivs = new ImageView[4]; // ��ʼ����������ͼƬ
		ivs[0] = (ImageView) view.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) view.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) view.findViewById(R.id.iv_3);
		ivs[3] = (ImageView) view.findViewById(R.id.iv_4);
		iv_des = new ImageView[4]; // ��ʼ��ÿ����Ŀ������ͼƬ
		iv_des[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		iv_des[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		iv_des[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		iv_des[3] = (ImageView) view.findViewById(R.id.des_iv_4);
		tv_des = new TextView[4]; // ��ʼ��ÿ����Ŀ�������ı�
		tv_des[0] = (TextView) view.findViewById(R.id.des_tv_1);
		tv_des[1] = (TextView) view.findViewById(R.id.des_tv_2);
		tv_des[2] = (TextView) view.findViewById(R.id.des_tv_3);
		tv_des[3] = (TextView) view.findViewById(R.id.des_tv_4);

		des_layout = new LinearLayout[4]; // ��ʼ����Ŀ���Բ���
		des_layout[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		des_layout[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		des_layout[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		des_layout[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) safe_content.getLayoutParams();
		params.height = 0;
		safe_content.setLayoutParams(params);
		safe_arrow.setImageResource(R.drawable.arrow_down);
		return view;
	}

	@Override
	public void refreshViews(AppInfo data) {
		//���õ��������
		safe_layout.setOnClickListener(this);

		List<String> safeUrl = data.getSafeUrl();
		List<String> safeDesUrl = data.getSafeDesUrl();
		List<String> safeDes = data.getSafeDes();
		List<Integer> safeDesColor = data.getSafeDesColor(); // 0 1 2 3
		for (int i = 0; i < 4; i++) {
			if (i < safeUrl.size() && i < safeDesUrl.size()
					&& i < safeDes.size() && i < safeDesColor.size()) {
				ivs[i].setVisibility(View.VISIBLE);
				des_layout[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(ivs[i], HttpHelper.URL + "image?name="
						+ safeUrl.get(i));
				bitmapUtils.display(iv_des[i], HttpHelper.URL + "image?name="
						+ safeDesUrl.get(i));
				tv_des[i].setText(safeDes.get(i));
				// ���ݷ�����������ʾ��ͬ����ɫ
				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0); // 00 00 00
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				tv_des[i].setTextColor(color);

			} else {
				ivs[i].setVisibility(View.GONE);
				des_layout[i].setVisibility(View.GONE);
			}

		}

	}

	boolean flag=false;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.safe_layout) {
			int  startHeight;
			int targetHeight;
			if (!flag) {    //  չ���Ķ���  
				startHeight=0;
				targetHeight=getMeasureHeight();
				flag = true;
				//safe_content.getMeasuredHeight();  //  0
			} else {
				flag=false;
				startHeight=getMeasureHeight();
				targetHeight=0;
			}
			// ֵ����
			ValueAnimator animator=ValueAnimator.ofInt(startHeight,targetHeight);
			final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) safe_content.getLayoutParams();
			animator.addUpdateListener(new AnimatorUpdateListener() {  // ����ֵ�ı仯
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					int value=(Integer) animator.getAnimatedValue();// ���е�ǰʱ����һ��ֵ
					layoutParams.height=value;
					safe_content.setLayoutParams(layoutParams);// ˢ�½���
					//System.out.println(value);
				}
			});
			
			animator.addListener(new AnimatorListener() {  // ��������ִ��
				//��������ʼִ�е�ʱ�����
				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onAnimationRepeat(Animator arg0) {
				}
				@Override
				public void onAnimationEnd(Animator arg0) {
					if(flag){
						safe_arrow.setImageResource(R.drawable.arrow_up);
					}else{
						safe_arrow.setImageResource(R.drawable.arrow_down);
					}
				}
				@Override
				public void onAnimationCancel(Animator arg0) {
				}
			});
			
			animator.setDuration(400);
			animator.start();
		}
	}
	
	//onMeasure()  �ƶ������Ĺ��� 
	// measure() ʵ�ʲ��� 
	/**
	 * ��ȡ�ؼ�ʵ�ʵĸ߶�
	 */
	public int getMeasureHeight(){
		int width = safe_content.getMeasuredWidth();  //  ���ڿ��Ȳ��ᷢ���仯  ���ȵ�ֵȡ����
		safe_content.getLayoutParams().height = LayoutParams.WRAP_CONTENT;//  �ø߶Ȱ�������

		//    ����1  �����ؼ�mode    ����2  ��С 
		int widthMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);  //  mode+size
		int heightMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);// �ҵĸ߶� �����1000
		// �������� ������һ����ȷ��ֵwidth, �߶������1000,��ʵ��Ϊ׼
		safe_content.measure(widthMeasureSpec, heightMeasureSpec); // ͨ���÷������²����ؼ� 
		return safe_content.getMeasuredHeight();
	}
}