package com.ityang.googlemarkrt.holder;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.domain.HomeTopImg;
import com.ityang.googlemarkrt.http.HttpHelper;
import com.ityang.googlemarkrt.utils.UiUtils;

import java.util.List;

/**
 * 主界面顶部的holder
 * Created by Administrator on 2015/10/1.
 */
public class HomeTopPicHolder extends BaseHolder<List<HomeTopImg>> implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    private ViewPager viewPager;
    private TextView topPic_text; //头条图片标题
    //private CirclePageIndicator indicator;
    private LinearLayout ll_home  ;
    private List<HomeTopImg> datas;
    private int currentItem;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            currentItem  =viewPager.getCurrentItem();
            int nextItem = ++currentItem;
            viewPager.setCurrentItem(nextItem);
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };

    public HomeTopPicHolder() {
        super();
    }

    @Override
    protected View initViews() {
       /* FrameLayout frameLayout = new FrameLayout(UiUtils.getContext());
        frameLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                UiUtils.dip2px(150)));
        viewPager = new ViewPager(UiUtils.getContext());
        viewPager.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,UiUtils.dip2px(150)));
        frameLayout.addView(viewPager);
        return frameLayout;*/
        View view  = View.inflate(UiUtils.getContext(),R.layout.list_header,null);
        topPic_text =(TextView) view.findViewById(R.id.topNew_text);

        ll_home  =(LinearLayout)view.findViewById(R.id.ll_home);
      /*  if(ll_home==null){
            System.out.println("ll_home==null");
        }else{
            System.out.println("ll_home!=null");
        }*/
        //indicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        viewPager = (ViewPager)view.findViewById(R.id.tabTopPager);
        return view;
    }

    @Override
    protected void refreshViews(List<HomeTopImg> datas) {
        this.datas = datas;
        viewPager.setAdapter(new MyPageAdapter());

        //topPic_text.setText(datas.get(0).getImgdes());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % (datas.size()));
        topPic_text.setText(datas.get(0).getImgdes());

        /*indicator.setViewPager(viewPager);
        indicator.setSnap(true);// 支持快照显示
        indicator.setOnPageChangeListener(this);
        indicator.onPageSelected(0);// 让指示器重新定位到第一个点*/
        viewPager.setOnPageChangeListener(this);
        viewPager.setOnTouchListener(this);

        for(int i=0;i<datas.size();i++){
            View dotView = new View(UiUtils.getContext());
            LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams(20,20);
            if(i==0){
                params.leftMargin=40;
            }
            dotView.setLayoutParams(params);
            dotView.setBackgroundResource(R.drawable.selector_dot);
            ll_home.addView(dotView);
        }
        for(int i=0;i<datas.size();i++){
            if(i==0){
                ll_home.getChildAt(i).setEnabled(true);
            }else {
                ll_home.getChildAt(i).setEnabled(false);
            }
        }

        //3秒钟自动切换viewpager
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        //改变标题
        topPic_text.setText(datas.get((position%datas.size())).getImgdes());
        //topPic_text.setText(datas.get(position).getImgdes());
        for(int i=0;i<datas.size();i++){
            if(i==(position%datas.size())){
                ll_home.getChildAt(i).setEnabled(true);
            }else {
                ll_home.getChildAt(i).setEnabled(false);
            }
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //取消图片轮播
                handler.removeMessages(0);
                break;
                //重新开启图片轮播
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(0,3000);
                break;
        }
        return false;
    }

    class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
           // return datas.size();
            return Integer.MAX_VALUE;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = new ImageView(UiUtils.getContext());
            bitmapUtils.display(img, HttpHelper.URL + "image?name=" + datas.get(position % datas.size()).getImgurl());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
