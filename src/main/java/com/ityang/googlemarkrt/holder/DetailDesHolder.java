package com.ityang.googlemarkrt.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class DetailDesHolder extends BaseHolder<AppInfo> implements OnClickListener {
    @ViewInject(R.id.des_content)
    private TextView des_content;
    @ViewInject(R.id.des_author)
    private TextView des_author;
    @ViewInject(R.id.des_arrow)
    private ImageView des_arrow;
    @ViewInject(R.id.des_layout)
    private RelativeLayout des_layout;

    @Override
    public View initViews() {
        View view = View.inflate(UiUtils.getContext(), R.layout.detail_des, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void refreshViews(AppInfo data) {
        des_content.setText(data.getDes());
        des_author.setText("开发者:" + data.getAuthor());
        des_layout.setOnClickListener(this);

        //des_content 起始高度7行的高度
        LayoutParams layoutParams = des_content.getLayoutParams();
        layoutParams.height = getShortMeasureHeight();
        des_content.setLayoutParams(layoutParams);
        des_arrow.setImageResource(R.drawable.arrow_down);
    }

    /**
     * 获取7行的高度
     *
     * @return
     */
    public int getShortMeasureHeight() {
        // 复制一个新的TextView 用来测量,最好不要在之前的TextView测量 有可能影响其它代码执行
        TextView textView = new TextView(UiUtils.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);//设置字体大小14dp
        textView.setMaxLines(6);
        textView.setLines(6);// 强制有7行
        int width = des_content.getMeasuredWidth(); // 开始宽度

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 获取TextView 自己本身的高度
     *
     * @return
     */
    public int getLongMeasureHeight() {
        int width = des_content.getMeasuredWidth(); // 开始宽度
        des_content.getLayoutParams().height = LayoutParams.WRAP_CONTENT;// 高度包裹内容

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);
        des_content.measure(widthMeasureSpec, heightMeasureSpec);//
        return des_content.getMeasuredHeight();
    }

    boolean flag = false;// true展开了 false 没有展开

    @Override
    public void onClick(View v) {
        expand();
    }

    ScrollView scrollView;
//	scrollView.scrollTo(0, scrollView.getMeasuredHeight())

    /**
     * 获取到界面的ScollView
     */
    public ScrollView getScrollView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            if (group instanceof ScrollView) {
                return (ScrollView) group;
            } else {
                return getScrollView(group);
            }

        } else {
            return null;
        }

    }

    private void expand() {
        scrollView = getScrollView(des_layout);
        int startHeight;
        int targetHeight;
        if (!flag) {
            flag = true;
            startHeight = getShortMeasureHeight();
            targetHeight = getLongMeasureHeight();
        } else {
            flag = false;
            startHeight = getLongMeasureHeight();
            targetHeight = getShortMeasureHeight();
        }
        final LayoutParams layoutParams = des_content.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                layoutParams.height = value;
                des_content.setLayoutParams(layoutParams);
                scrollView.scrollTo(0, scrollView.getMeasuredHeight());// 让scrollView 移动到最下面
            }
        });
        animator.addListener(new AnimatorListener() {  // 监听动画执行
            //当动画开始执行的时候调用
            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onAnimationRepeat(Animator arg0) {

            }
            @Override
            public void onAnimationEnd(Animator arg0) {
                if (flag) {
                    des_arrow.setImageResource(R.drawable.arrow_up);
                } else {
                    des_arrow.setImageResource(R.drawable.arrow_down);
                }
            }
            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
        animator.setDuration(500);//设置动画持续时间
        animator.start();
    }
}	
