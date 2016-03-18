package com.ityang.googlemarkrt;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.ityang.googlemarkrt.domain.AppInfo;
import com.ityang.googlemarkrt.holder.DetailBootomHolder;
import com.ityang.googlemarkrt.holder.DetailDesHolder;
import com.ityang.googlemarkrt.holder.DetailInfoHolder;
import com.ityang.googlemarkrt.holder.DetailSafeHolder;
import com.ityang.googlemarkrt.holder.DetailScreenHolder;
import com.ityang.googlemarkrt.manager.ThreadPoolManger;
import com.ityang.googlemarkrt.protocol.DetailProtocol;
import com.ityang.googlemarkrt.utils.BitmapUtilHelper;
import com.ityang.googlemarkrt.utils.LogUtils;
import com.ityang.googlemarkrt.utils.UiUtils;
import com.lidroid.xutils.BitmapUtils;

public class DetailActivity extends BaseActivity{
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
    private String packageName;
    private AppInfo appInfo;
    public void initViews() {
        //�ٴν���Ӧ������ҳ��ʹ״̬�ı�Ϊunknow
        state = STATE_UNKOWN;
        bitmapUtils = BitmapUtilHelper.getBitmapUtils();
        if (frameLayout == null) {
            frameLayout = new FrameLayout(DetailActivity.this);
            init();
        }
        frameLayout = new FrameLayout(DetailActivity.this);
        init();
        setContentView(frameLayout);
        show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();  // ��ȡ���򿪵�ǰactivity����ͼ����
        packageName = intent.getStringExtra("packagename");
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void init() {
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
                frameLayout.removeAllViews();
                frameLayout.addView(successView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                successView.setVisibility(View.VISIBLE);
            }
        }
    }



    /* �����˿յĽ��� */
    private View createEmptyView() {
        View view = View.inflate(this, R.layout.loadpage_empty, null);
        return view;
    }

    /* �����˴������ */
    private View createErrorView() {
        View view = View.inflate(this, R.layout.loadpage_error, null);
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
                .inflate(this, R.layout.loadpage_loading, null);
        return view;
    }


    private void show() {
        LogUtils.i("state=" + state);
        if (state == STATE_EMPTY || state == STATE_ERROR) {
            state = STATE_LOADING;
        }
        //�����̳߳�ʵ��
        ThreadPoolManger.getInstance().creatLongPool().execute(new Runnable() {
            @Override
            public void run() {
                //ģ����ʷ�����ʱ��
                //SystemClock.sleep(2000);
                int result = getDataFromServer();
                state = result;
                if (UiUtils.getContext() != null) {
                    runOnUiThread(new Runnable() {
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

    private FrameLayout bottom_layout,detail_info,detail_safe,detail_des;
    private HorizontalScrollView detail_screen;
    private DetailInfoHolder detailInfoHolder;
    private DetailScreenHolder screenHolder;
    private DetailSafeHolder safeHolder;
    private DetailDesHolder desHolder;
    private DetailBootomHolder bootomHolder;
    /**
     * ���سɹ�����
     *
     * @return
     */
    private View createSuccessView() {
        View view=View.inflate(this,R.layout.activity_detail,null);
        bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
        detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
        detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
        detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
        detail_screen = (HorizontalScrollView)view.findViewById(R.id.detail_screen);

        detailInfoHolder = new DetailInfoHolder();
        detail_info.addView(detailInfoHolder.getContentView());
        detailInfoHolder.showViews(appInfo);

        screenHolder = new DetailScreenHolder();
        detail_screen.addView(screenHolder.getContentView());
        screenHolder.showViews(appInfo);

        safeHolder = new DetailSafeHolder();
        detail_safe.addView(safeHolder.getContentView());
        safeHolder.showViews(appInfo);

        desHolder = new DetailDesHolder();
        detail_des.addView(desHolder.getContentView());
        desHolder.showViews(appInfo);

        bootomHolder = new DetailBootomHolder();
        bottom_layout.addView(bootomHolder.getContentView());
        bootomHolder.showViews(appInfo);
        return view;
    }
    /**
     * �ӷ�������ȡ����
     *
     * @return
     */
    private int getDataFromServer() {
        SystemClock.sleep(500);
        DetailProtocol protocol = new DetailProtocol(packageName);
        appInfo =  protocol.load(0);
        return checkData(appInfo);
    }

    protected int checkData(AppInfo data){
        if (data == null) {
            return STATE_ERROR;
        } else {
            return STATE_SUCCESS;
        }
    }
}
