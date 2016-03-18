package com.ityang.googlemarkrt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2015/10/14.
 */
public class MyFrameLayout extends FrameLayout {
    //��߱���
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

        //����Ǿ�ȷ���������Կ��Ϊ��׼
        if(widthMode == MeasureSpec.EXACTLY && heightMode!=MeasureSpec.EXACTLY){
            height =(int)(( width / ratio)+0.5f);

        }else if(heightMode == MeasureSpec.EXACTLY && widthMode!=MeasureSpec.EXACTLY){
            //�߶��Ǿ�ȷ���������Ը߶�Ϊ��׼
            width = (int)((height * ratio)+0.5f);
        }

        widthMeasureSpec =  MeasureSpec.makeMeasureSpec(width+getPaddingRight()+
                getPaddingLeft(),MeasureSpec.EXACTLY);

        heightMeasureSpec =  MeasureSpec.makeMeasureSpec(height+getPaddingTop()+
                getPaddingBottom(),MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);*/
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); // ģʽ
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);// ��ȴ�С
        int width = widthSize - getPaddingLeft() - getPaddingRight();// ȥ���������ߵ�padding

        int heightMode = MeasureSpec.getMode(heightMeasureSpec); // ģʽ
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);// �߶ȴ�С
        int height = heightSize - getPaddingTop() - getPaddingBottom();// ȥ���������ߵ�padding

        if (widthMode == MeasureSpec.EXACTLY
                && heightMode != MeasureSpec.EXACTLY) {
            // ����һ�� �߶ȵ�ֵ �ø߶�=���/����
            height = (int) (width / ratio + 0.5f); // ��֤4������
        } else if (widthMode != MeasureSpec.EXACTLY
                && heightMode == MeasureSpec.EXACTLY) {
            // ���ڸ߶��Ǿ�ȷ��ֵ ,������Ÿ߶ȵı仯���仯
            width = (int) ((height * ratio) + 0.5f);
        }
        // �����������µĹ���
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
                width + getPaddingLeft() + getPaddingRight());
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
                height + getPaddingTop() + getPaddingBottom());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
