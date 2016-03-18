package com.ityang.googlemarkrt;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ityang.googlemarkrt.factory.FragmentFactory;
import com.ityang.googlemarkrt.holder.MenuHolder;
import com.ityang.googlemarkrt.utils.UiUtils;


public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private DrawerLayout drawLayout;
    private FrameLayout home_fl;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager mViewPager;
    private PagerTabStrip pager_tab_strip;
    private String[] tabItems = UiUtils.getStringArray(R.array.tab_names);
    @Override
    public void init() {
        super.init();
    }
    public void initViews() {
        setContentView(R.layout.activity_main);
        drawLayout = (DrawerLayout)findViewById(R.id.drawLayout);
        home_fl = (FrameLayout)findViewById(R.id.home_fl);
        mViewPager = (ViewPager)findViewById(R.id.vp_main);
        pager_tab_strip=(PagerTabStrip) findViewById(R.id.pager_tab_strip);
        // ���ñ�ǩ����
        pager_tab_strip.setTextSize(TypedValue.COMPLEX_UNIT_PX, UiUtils.dip2px(18));
        //���ñ�ǩ�»��ߵ���ɫ
        pager_tab_strip.setTabIndicatorColor(getResources().getColor(R.color.indicatorcolor));

        //�л��໬�˵��Ŀ���
        drawerToggle = new ActionBarDrawerToggle(this, drawLayout, R.drawable.ic_drawer_am,
                R.string.open_darwlayout,
                R.string.close_darwlayout){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        MenuHolder menuHolder = new MenuHolder();
        home_fl.addView(menuHolder.getContentView());
    }
    @Override
    public void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_google_play, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        //���ü����¼�
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * ����س�ʱ�ص�
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "�ύ����" + query, Toast.LENGTH_SHORT).show();
        return false;
    }
    /**
     * �����ı��ı�ʱ����
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this,"���µ�����"+newText,Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * ��������
     */
    class MyFragmentAdapter extends FragmentStatePagerAdapter {


        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.newInstance().getFragment(position);
        }

        @Override
        public int getCount() {
            return tabItems.length;
        }
        /**
         * ��д�˷���,����ҳ�����,����viewpagerIndicator��ҳǩ��ʾ
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabItems[position];
        }
    }

    /*
   ������back���˳�
    */
    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()���ۺ�ʱ���ã��϶�����2000
            {
                Toast.makeText(MainActivity.this, "�ٰ�һ���˳�MyAppStore", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
