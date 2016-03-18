package com.ityang.googlemarkrt.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Administrator on 2015/10/22.
 */
public class DrawableUtil {
    private DrawableUtil(){

    }
    /**
     * ���봴��shape
     * @return
     */
    public static GradientDrawable getShape(int color){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(UiUtils.dip2px(5));
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }
    /**
     * ���봴��ѡ����
     * @return
     */
    public static StateListDrawable getStateDrawable(Drawable pressedDrawable,Drawable normalDrawable ){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},pressedDrawable);
        stateListDrawable.addState(new int[]{},normalDrawable);
        return stateListDrawable;
    }
}
