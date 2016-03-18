package com.ityang.googlemarkrt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ityang.googlemarkrt.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class TagLayout2 extends ViewGroup {
    private int horizontolSpacing = UiUtils.dip2px(13);
    private int verticalSpacing = UiUtils.dip2px(13);

    public TagLayout2(Context context) {
        super(context);
    }

    public TagLayout2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private Line currentline;// 当前的行
    private int useWidth = 0;// 当前行使用的宽度
    private List<Line> mLines = new ArrayList<TagLayout2.Line>();
    private int width;

    public TagLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 测量 当前控件Flowlayout
    // 父类是有义务测量每个孩子�?
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		MeasureSpec.EXACTLY;
//		MeasureSpec.AT_MOST;
//		MeasureSpec.UNSPECIFIED;
        mLines.clear();
        currentline = null;
        useWidth = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);  //  获取当前父容�?(Flowlayout)的模�?
        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop(); // 获取到宽和高
        int childeWidthMode;
        int childeHeightMode;
        //  为了测量每个孩子 �?要指定每个孩子测量规�?
        childeWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;
        childeHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childeWidthMode, width);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childeHeightMode, height);

        currentline = new Line();// 创建了第�?�?
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            // 测量每个孩子
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            int measuredWidth = child.getMeasuredWidth();
            useWidth += measuredWidth;// 让当前行加上使用的长�?
            if (useWidth <= width) {
                currentline.addChild(child);//这时候证明当前的孩子是可以放进当前的行里,放进�?
                useWidth += horizontolSpacing;
                if (useWidth > width) {
                    //换行
                    newLine();
                }
            } else {
                //换行
                if (currentline.getChildCount() < 1) {
                    currentline.addChild(child);  // 保证当前行里面最少有�?个孩�?
                }
                newLine();
            }

        }
        if (!mLines.contains(currentline)) {
            mLines.add(currentline);// 添加�?后一�?
        }
        int totalheight = 0;
        for (Line line : mLines) {
            totalheight += line.getHeight();
        }
        totalheight += verticalSpacing * (mLines.size() - 1) + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), resolveSize(totalheight, heightMeasureSpec));
    }

    private void newLine() {
        mLines.add(currentline);// 记录之前的行
        currentline = new Line(); // 创建新的�?�?
        useWidth = 0;
    }

    private class Line {
        int height = 0; //当前行的高度
        int lineWidth = 0;
        private List<View> children = new ArrayList<View>();

        /**
         * 添加�?个孩�?
         *
         * @param child
         */
        public void addChild(View child) {
            children.add(child);
            if (child.getMeasuredHeight() > height) {
                height = child.getMeasuredHeight();
            }
            lineWidth += child.getMeasuredWidth();
        }

        public int getHeight() {
            return height;
        }

        /**
         * 返回孩子的数�?
         *
         * @return
         */
        public int getChildCount() {
            return children.size();
        }

        public void layout(int l, int t) {
            lineWidth += horizontolSpacing * (children.size() - 1);
            int surplusChild = 0;
            int surplus = width - lineWidth;
            if (surplus > 0) {
                if (children.size() > 0) {
                    surplusChild = surplus / children.size();
                } else if (children.size() == 0) {
                    surplusChild = surplus;
                }
            }
            //Log.i("TagLayout2","childrenSize = "+children.size());
            // System.out.println("childrenSize = "+children.size());
            //surplusChild = surplus / 4;
            for (int i = 0; i < children.size(); i++) {
                View child = children.get(i);
                //  getMeasuredWidth()   控件实际的大�?
                // getWidth()  控件显示的大�?

                child.layout(l, t, l + child.getMeasuredWidth() + surplusChild, t + child.getMeasuredHeight());
                l += (child.getMeasuredWidth() + horizontolSpacing + surplusChild);
            }
        }

    }

    // 分配每个孩子的位�?
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        l += getPaddingLeft();
        t += getPaddingTop();
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            line.layout(l, t);  //交给每一行去分配
            t += line.getHeight() + verticalSpacing;
        }
    }
}
