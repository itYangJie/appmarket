package com.ityang.googlemarkrt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SplashActivity extends Activity {

    private RelativeLayout splash_bg;
    private TextView splash_tv;

    private ImageView splash_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        startAnimation();
    }


    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_splash);
        //MyApplication application = (MyApplication) getApplication();

        splash_bg = (RelativeLayout) findViewById(R.id.splash_bg);
        splash_tv = (TextView) findViewById(R.id.splash_tv);
        splash_img = (ImageView) findViewById(R.id.splash_img);
    }

    /**
     * 开启动画效果
     */
    private void startAnimation() {
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(1500);
        alpha.setFillAfter(true);
        splash_bg.startAnimation(alpha);

        TranslateAnimation ta_tv = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -0.45f);
        ta_tv.setDuration(1000);
        ta_tv.setFillAfter(true);
        splash_tv.startAnimation(ta_tv);

        TranslateAnimation ta_img = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF,1.5f);
        ta_img.setDuration(1000);
        ta_img.setFillAfter(true);
        splash_img.startAnimation(ta_img);

        //设置动画监听器
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束,判断是否是第一次打开应用，如果是则进入新手引导页
                //进入主界面
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
