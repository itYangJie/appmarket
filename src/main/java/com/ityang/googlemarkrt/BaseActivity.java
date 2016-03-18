package com.ityang.googlemarkrt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * ����activity
 * Created by Administrator on 2015/9/13.
 */
public class BaseActivity extends ActionBarActivity {
    //�������activity�ļ���
    public static final List<BaseActivity> activityList = new LinkedList<BaseActivity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (activityList) {
            activityList.add(this);
        }
        initViews();
        initActionBar();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (activityList) {
            activityList.remove(this);
        }
    }

    public void killAll() {
        // ������һ��activityList ����
        List<BaseActivity> copy;
        synchronized (activityList) {
            copy = new LinkedList<BaseActivity>(activityList);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // ɱ����ǰ�Ľ���
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void init() {

    }

    public void initViews() {

    }

    public void initActionBar() {

    }

    public void initDatas() {

    }

}
