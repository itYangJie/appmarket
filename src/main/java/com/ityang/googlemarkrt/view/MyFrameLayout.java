package com.ityang.googlemarkrt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2015/10/14.
 */
public class MyFrameLayout extends FrameLayout {
    //宽高比例
    private final float ratio = 2.43f;

    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       /* int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = widthSize-getPaddingLeft()-getPaddingRight();

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = heightSize-getPaddingTop()-getPaddingBottom();

        //宽度是精确测量，则以宽度为基准
        if(widthMode == MeasureSpec.EXACTLY && heightMode!=MeasureSpec.EXACTLY){
            height =(int)(( width / ratio)+0.5f);

        }else if(heightMode == MeasureSpec.EXACTLY && widthMode!=MeasureSpec.EXACTLY){
            //高度是精确测量，则以高度为基准
            width = (int)((height * ratio)+0.5f);
        }

        widthMeasureSpec =  MeasureSpec.makeMeasureSpec(width+getPaddingRight()+
                getPaddingLeft(),MeasureSpec.EXACTLY);

        heightMeasureSpec =  MeasureSpec.makeMeasureSpec(height+getPaddingTop()+
                getPaddingBottom(),MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);*/
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); // 模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);// 宽度大小
        int width = widthSize - getPaddingLeft() - getPaddingRight();// 去掉左右两边的padding

        int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);// 高度大小
        int height = heightSize - getPaddingTop() - getPaddingBottom();// 去掉上下两边的padding

        if (widthMode == MeasureSpec.EXACTLY
                && heightMode != MeasureSpec.EXACTLY) {
            // 修正一下 高度的值 让高度=宽度/比例
            height = (int) (width / ratio + 0.5f); // 保证4舍五入
        } else if (widthMode != MeasureSpec.EXACTLY
                && heightMode == MeasureSpec.EXACTLY) {
            // 由于高度是精确的值 ,宽度随着高度的变化而变化
            width = (int) ((height * ratio) + 0.5f);
        }
        // 重新制作了新的规则
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
                width + getPaddingLeft() + getPaddingRight());
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
                height + getPaddingTop() + getPaddingBottom());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
