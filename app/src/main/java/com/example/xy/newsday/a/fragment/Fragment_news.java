package com.example.xy.newsday.a.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.activity.Activity_AddLables;
import com.example.xy.newsday.a.adapter.FragmentViewpagerAdapter;
import com.example.xy.newsday.a.bean.MyDatas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xy on 2016/11/15.
 */
public class Fragment_news extends Fragment {
    View view=null;
    ArrayList<String> titles;//标题列表
    static ArrayList<Fragment> fragments; //页面列表
    FragmentViewpagerAdapter adapter;//声明适配器
    ImageView imageViewss;
    //声明控件
    ViewPager viewPager;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_fragment_news,container,false);
        initView();
        //标签的点击事件
        imageViewss= (ImageView) view.findViewById(R.id.add_image);
        imageViewss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Activity_AddLables.class);
                startActivity(intent);
            }
        });
        return view;
    }
    //初始化控件
    private void initView() {
        initData();
        //创建适配器
        adapter=new FragmentViewpagerAdapter(getActivity().getSupportFragmentManager(),titles,fragments);
        viewPager= (ViewPager) view.findViewById(R.id.my_ViewPager);
        viewPager.setAdapter(adapter);
        tabLayout= (TabLayout) view.findViewById(R.id.tapLayout);
        for (int i=0;i<titles.size();i++)
        {
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        tabLayout.setupWithViewPager(viewPager);
        //为tabLayout设置模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initData() {
        //创建标题的集合
        titles=new ArrayList<>();
        MyDatas myDatas=new MyDatas(getContext());
        titles= (ArrayList<String>) myDatas.getMyData();
        Log.d("Fragment_news", "titles.size():" + titles.size());
        //必须要的它的id
        List<String> list=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("国内最新","5572a109b3cdc86cf39001db");
        map.put("体育最新","5572a109b3cdc86cf39001e6");
        map.put("互联网最新","5572a109b3cdc86cf39001e3");
        map.put("社会最新","5572a10bb3cdc86cf39001f8");
        map.put("国内焦点","5572a108b3cdc86cf39001cd");
        map.put("健康养生","5572a10ab3cdc86cf39001f3");
        for (int x=0;x<titles.size();x++)
        {
            String value=titles.get(x);
            for (String values:map.keySet() ) {
                if (values.equals(value))
                {
                    list.add(map.get(values));
                }
            }
        }

        //创建fragment的集合
        fragments=new ArrayList<>();
        for (String title:list) {
            Fragment fragment=new CurrentFragment();
            //通过Bundle传值
            Bundle bundle=new Bundle();
            bundle.putString("title",title);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        Log.e("fragments","添加完毕");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentnews", "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
