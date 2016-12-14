package com.example.xy.newsday.a.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by xy on 2016/11/15.
 */
public class FragmentViewpagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> titles;
    ArrayList<Fragment> fragments;
    public FragmentViewpagerAdapter(FragmentManager fm,ArrayList<String> titles,ArrayList<Fragment> fragments) {
        super(fm);
        this.titles=titles;
        this.fragments=fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    //返回标题数据的长度
    @Override
    public int getCount() {
        return titles.size();
    }

    public CharSequence getPageTitle(int position)
    {
        return titles.get(position);
    }
}
