package com.ityang.googlemarkrt.factory;

import android.support.v4.app.Fragment;

import com.ityang.googlemarkrt.fragment.AppFragment;
import com.ityang.googlemarkrt.fragment.CategoryFragment;
import com.ityang.googlemarkrt.fragment.GameFragment;
import com.ityang.googlemarkrt.fragment.HomeFragment;
import com.ityang.googlemarkrt.fragment.SubjectFragment;
import com.ityang.googlemarkrt.fragment.TopFragment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/13.
 */
public class FragmentFactory {

    private  static FragmentFactory factory = new FragmentFactory();
    //�ڴ滺�棬���Ч��
    private Map<Integer,Fragment> fragmentMap = new LinkedHashMap<Integer,Fragment>();
    private FragmentFactory(){

    }

    public static FragmentFactory newInstance(){
        return factory;
    }

    /**
     * ����������fragment����
     * @param position
     * @return
     */
    public Fragment getFragment (int position){
        Fragment fragment = null;
        fragment = fragmentMap.get(position);
        if(fragment==null){
            //�ڴ���û�л���
            switch (position){
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new CategoryFragment();
                    break;
                case 5:
                    fragment = new TopFragment();
                    break;
            }
            //���浽�ڴ���
            fragmentMap.put(position,fragment);
        }
        return fragment;
    }

}
