package com.ityang.googlemarkrt.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ityang.googlemarkrt.R;
import com.ityang.googlemarkrt.manager.ThreadPoolManger;
import com.ityang.googlemarkrt.utils.BitmapUtilHelper;
import com.ityang.googlemarkrt.utils.LogUtils;
import com.ityang.googlemarkrt.utils.ViewUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public abstract class BaseFragment extends Fragment {

    public static final int STATE_UNKOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public static int state = STATE_UNKOWN;
    private View loadingView;// �����еĽ���
    private View errorView;// �������
    private View emptyView;// �ս���
    private View successView;// ���سɹ��Ľ���
    private FrameLayout frameLayout;
    protected BitmapUtils bitmapUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bitmapUtils = BitmapUtilHelper.getBitmapUtils();
        //LogUtils.i("������oncreateView����");
        if (frameLayout == null) {
            // LogUtils.i("framelayout==null");
            frameLayout = new FrameLayout(getActivity());
            init();
        } else {
            // LogUtils.i("framelayout!=null");
            ViewUtils.removeParent(frameLayout);
        }
        show();
        return frameLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        loadingView = createLoadingView(); // �����˼����еĽ���
        if (loadingView != null) {
            frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        errorView = createErrorView(); // ���ش������
        if (errorView != null) {
            frameLayout.addView(errorView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        emptyView = createEmptyView(); // ���ؿյĽ���
        if (emptyView != null) {
            frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        showPage();
    }

    /**
     * ����״̬�л�����
     */
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(state == STATE_UNKOWN
                    || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (state == STATE_SUCCESS) {
            successView = createSuccessView();
            if (successView != null) {
                /*try {
                    frameLayout.removeView(successView);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                frameLayout.removeAllViews();
                frameLayout.addView(successView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                successView.setVisibility(View.VISIBLE);
            }
        }
    }


    /* �����˿յĽ��� */
    private View createEmptyView() {
        View view = View.inflate(getActivity(), R.layout.loadpage_empty, null);
        return view;
    }

    /* �����˴������ */
    private View createErrorView() {
        View view = View.inflate(getActivity(), R.layout.loadpage_error, null);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    /* ���������еĽ��� */
    private View createLoadingView() {
        View view = View
                .inflate(getActivity(), R.layout.loadpage_loading, null);
        return view;
    }


    private void show() {
        LogUtils.i("state=" + state);
        if (state == STATE_EMPTY || state == STATE_ERROR) {
            state = STATE_LOADING;
        }
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                //ģ����ʷ�����ʱ��
                SystemClock.sleep(2000);
                int result = getDataFromServer();
                state = result;
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showPage();
                        }
                    });
                }
            }
        }).start();*/
        //�����̳߳�ʵ��
        ThreadPoolManger.getInstance().creatLongPool().execute(new Runnable() {
            @Override
            public void run() {
                //ģ����ʷ�����ʱ��
                //SystemClock.sleep(2000);
                int result = getDataFromServer();
                state = result;
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showPage();
                        }
                    });
                }
            }
        });
        showPage();
    }

    /**
     * ���سɹ�����
     *
     * @return
     */
    protected abstract View createSuccessView();

    /**
     * �ӷ�������ȡ����
     *
     * @return
     */
    protected abstract int getDataFromServer();

    protected int checkData(List datas){
        if (datas == null) {
            return STATE_ERROR;
        } else if (datas.size() == 0) {
            return STATE_EMPTY;
        } else {
            return STATE_SUCCESS;
        }
    }
}
