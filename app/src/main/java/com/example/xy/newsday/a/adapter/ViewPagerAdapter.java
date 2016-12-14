package com.example.xy.newsday.a.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/15.
 */
public class ViewPagerAdapter extends PagerAdapter {

    List<View> list=new ArrayList<>();
    public ViewPagerAdapter(List<View> list) {
        this.list = list;
    }

    //返回要滑动视图的个数
    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //移除指定位置的页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    //创建指定页面的视图
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

}
