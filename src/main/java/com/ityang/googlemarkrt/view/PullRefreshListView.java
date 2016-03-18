package com.ityang.googlemarkrt.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.manager.ThreadPoolManger;
import com.ityang.googlemarkrt.utils.UiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * �Զ��������ˢ�µ�ListView
 * Created by Administrator on 2015/8/18.
 */
public abstract class PullRefreshListView<T> extends BaseListView {

    private int headerViewHeight;
    private int downY;//����ʱy����
    private View headerView;
    //private OnRefreshingListener listener = null;
    private ImageView iv_arrow;
    private ProgressBar pb_rotate;
    private TextView tv_state, tv_time;
    public final int PULL_REFRESH = 0;//����ˢ�µ�״̬
    private final int RELEASE_REFRESH = 1;//�ɿ�ˢ�µ�״̬
    private final int REFRESHING = 2;//����ˢ�µ�״̬
    private int currentState = PULL_REFRESH;
    //����Ч��
    private Animation upAnimation;
    private Animation downAnimation;

    public PullRefreshListView(Context context) {
        super(context);
        initView();
        initRotateAnimation();
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initRotateAnimation();
    }

    /**
     * Ϊlistview ���headerview
     */
    private void initView() {
        initHeader();
    }

    /**
     * ��ʼ��headerview
     */
    private void initHeader() {
        //�Ӳ����ļ��м���headerview
        headerView = View.inflate(getContext(), R.layout.header_lrefreshlist, null);

        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
        tv_state = (TextView) headerView.findViewById(R.id.tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);
        //֪ͨϵͳ����
        headerView.measure(0, 0);
        //������headerview�ĸ߶�
        headerViewHeight = headerView.getMeasuredHeight();
        //Ĭ������Ӱ��headerview
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        this.addHeaderView(headerView);
    }

    /**
     * �����¼�����
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //��¼����ʱ��������
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //System.out.println(headerView.getPaddingTop());
                //����״̬���ܻ���
                if (currentState == REFRESHING) {
                    break;
                }
                //�����ƶ��ľ���
                int moveY = (int) (ev.getY()) - downY;
                //�����paddingTopֵ
                int paddingTop = moveY - headerViewHeight;
                //����headerview��ȫ���غ���Ȼ���������ƶ�
                if (paddingTop > -headerViewHeight && getFirstVisiblePosition() == 0) {
                    headerView.setPadding(0, paddingTop, 0, 0);
                    if (paddingTop > 0 && currentState == PULL_REFRESH) {
                        //�ı�״̬Ϊ����ˢ��
                        currentState = RELEASE_REFRESH;
                        changeHeaderView();
                    } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {
                        //�ı�״̬Ϊ����ˢ��
                        currentState = PULL_REFRESH;
                        changeHeaderView();
                    }

                    return true;//����TouchMove������listview����ô�move�¼�,�����listview�޷�����
                }

                break;

            case MotionEvent.ACTION_UP:
                //�ж�״̬
                if (currentState == PULL_REFRESH) {
                    //������ˢ��״̬������ ��ȫ����
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                } else if (currentState == RELEASE_REFRESH) {
                    //������ˢ��״̬�����֣��������״̬
                    currentState = REFRESHING;
                    //��ȫ��ʾheaderview
                    headerView.setPadding(0, 0, 0, 0);
                    changeHeaderView();
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * ��ʼ����ת����
     */
    private void initRotateAnimation() {
        upAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);
        downAnimation = new RotateAnimation(-180, -360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }

    /**
     * �ı�headerview��ʾЧ��
     */
    private void changeHeaderView() {
        switch (currentState) {
            case PULL_REFRESH:
                tv_state.setText("����ˢ��");
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                tv_state.setText("�ɿ�ˢ��");
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.clearAnimation();//��Ϊ���ϵ���ת�����п���û��ִ����
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_rotate.setVisibility(View.VISIBLE);
                tv_state.setText("����ˢ��...");

                // ���������������
                ThreadPoolManger.getInstance().creatLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(600);
                        final List<T> refreshDatas = pullRefresh();
                        //�����߳��и�������
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (refreshDatas == null) {
                                    Toast.makeText(UiUtils.getContext(), "�Բ���ˢ��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
                                } else if (refreshDatas.size() == 0) {
                                    Toast.makeText(UiUtils.getContext(), "�Բ���ʲô��û��ˢ����", Toast.LENGTH_SHORT).show();

                                } else {
                                    // �ɹ���
                                    onRefreshSuccess(refreshDatas);
                                    Toast.makeText(UiUtils.getContext(), "��ϲ��ˢ�³ɹ���", Toast.LENGTH_SHORT).show();
                                }
                                changeStateToStart();
                            }
                        });
                    }
                });

                break;
        }
    }

    protected abstract void onRefreshSuccess(List<T> refreshDatas);

    protected abstract List<T> pullRefresh();

    /**
     * �˺����������ã����ݸ�����Ϻ�ı�״̬
     */
    public void changeStateToStart() {
        //����headerView״̬
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        currentState = PULL_REFRESH;
        pb_rotate.setVisibility(View.INVISIBLE);
        iv_arrow.setVisibility(View.VISIBLE);
        tv_state.setText("����ˢ��");
        tv_time.setText("���ˢ�£�" + new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));
    }

   /* *//**
     * ����ˢ�¼�����
     *
     * @param listener
     *//*
    public void setRefreshingListener(OnRefreshingListener listener) {
        this.listener = listener;
    }*/

   /* *//**
     * ��¶�����Ľӿ�
     *//*
    public interface OnRefreshingListener {
        void onRefreshing();
    }*/

   /* *//**
     * ������ȡ��ǰ״̬
     * @return
     *//*
    public boolean canClick(){
        int paddingTop = headerView.getPaddingTop();

        System.out.println("paddingTop��"+paddingTop);

        if(paddingTop<=(-headerViewHeight)){
            return true;
        }
        return true;
    }*/
}
