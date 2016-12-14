package com.example.xy.newsday.a.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.adapter.TagsAdapter;
import com.example.xy.newsday.a.adapter.TagtopAdapter;
import com.example.xy.newsday.a.bean.MyDatas;
import com.example.xy.newsday.a.bean.RecycleViewDivider;
import com.example.xy.newsday.a.fragment.CurrentFragment;
import com.example.xy.newsday.a.fragment.Fragment_news;
import com.ismaeltoe.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xy on 2016/11/22.
 */
public class Activity_AddLables extends AppCompatActivity{
    //声明控件
    Context context;
    Toolbar myToolBar;
    final int i=0;
    TagsAdapter tagsAdapter;
    TagtopAdapter tagtopAdapter;
    List<String> list;
    List mDatas;
    RecyclerView recyclerViewTop;
    RecyclerView recycleraView;
    ImageView images;//返回按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlabels);
        if (context==null)
        {
            context=this;
        }
        initData();
        initView();
    }

    private void initData() {
        //创建集合，添加数据(当前标签)
        mDatas=new ArrayList<>(Arrays.asList("国内最新","体育最新","互联网最新",
                "社会最新","国内焦点","健康养生"));
        //创建集合，添加数据(添加感兴趣的标签)
        list=new ArrayList<>();
        MyDatas myDatas=new MyDatas(context);
        list=myDatas.getMyData();
    }
    //加载控件
    private void initView() {
        myToolBar= (Toolbar) findViewById(R.id.myToolbars);
        images= (ImageView) findViewById(R.id.lable_images);
        //返回按钮的监听
        images.setOnClickListener(new MyOnClickss());
        //声明控件
        recyclerViewTop= (RecyclerView) findViewById(R.id.myTop_recycler);
        //设置当前标签的布局样式（瀑布流布局）
        recyclerViewTop.setLayoutManager(new GridLayoutManager(context,2));
        tagtopAdapter=new TagtopAdapter(context,list);
        recyclerViewTop.setAdapter(tagtopAdapter);
        //当前标签的点击事件
        tagtopAdapter.setMyOnClicks(new TagtopAdapter.MyOnClick() {
            @Override
            public void onClick(int position) {
                list.remove(position);
                MyDatas myDatas=new MyDatas(context);
                myDatas.mySetData(list);
                tagtopAdapter.notifyDataSetChanged();
            }
        });

        recycleraView= (RecyclerView) findViewById(R.id.myTagsRecycler);
        //设置标签的布局样式（线性布局）
        recycleraView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        //创建适配器
        tagsAdapter=new TagsAdapter(context,mDatas);
//添加新的标签点击事件
        tagsAdapter.setMyOnClicks(new TagsAdapter.MyOnClick() {
            @Override
            public void onClick(int position) {
                String content= (String) mDatas.get(position);
                for (int i=0;i<list.size();i++)
                {
                    if (!content.equals(list.get(i)))
                    {
                        list.add((String) mDatas.get(position));
                    }
                }
                MyDatas myDatas=new MyDatas(context);
                myDatas.mySetData(list);
                tagtopAdapter.notifyDataSetChanged();
            }
        });
        recycleraView.setAdapter(tagsAdapter);
        //添加分隔线
        recycleraView.addItemDecoration(new RecycleViewDivider(Activity_AddLables.this,LinearLayoutManager.VERTICAL));
    }

    private class MyOnClickss implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Activity_AddLables.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
