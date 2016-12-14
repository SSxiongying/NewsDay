package com.example.xy.newsday.a.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.adapter.ViewPagerAdapter;
import com.example.xy.newsday.a.bean.MyDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/15.
 */
public class activity_viewpager extends AppCompatActivity {

    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    LayoutInflater inflater;
    List<View> list=new ArrayList<>();//创建View的集合
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        List<String> titles=new ArrayList<>();
        titles.add("国内最新");
        titles.add("体育最新");
        titles.add("互联网最新");
        titles.add("社会最新");
        titles.add("国内焦点");
        titles.add("健康养生");
        MyDatas myDatas=new MyDatas(this);
        myDatas.mySetData(titles);//保存数据
        viewPager = (ViewPager) findViewById(R.id.myViewpager);//找到控件
        inflater= LayoutInflater.from(this);//加载布局
        View view_one = inflater.inflate(R.layout.view_one, null);//页面一
        View view_second = inflater.inflate(R.layout.view_second, null);//页面二
        View view_there = inflater.inflate(R.layout.view_there, null);//页面三
        //将view视图添加到集合
        list.add(view_one);
        list.add(view_second);
        list.add(view_there);
        //适配器的声明
        adapter = new ViewPagerAdapter(list);
        viewPager.setAdapter(adapter);
        //新闻页面的跳转
        button= (Button) view_there.findViewById(R.id.btn_welc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_viewpager.this,Activity_welcome.class);
                startActivity(intent);
            }
        });
    }
}
