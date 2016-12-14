package com.example.xy.newsday.a.bean;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by xy on 2016/11/23.
 */
public class MyDatas {
    Context context;
    public MyDatas(Context context) {
        this.context=context;
    }
    //存储数据
    @TargetApi(Build.VERSION_CODES.M)
    public  void mySetData(List<String> list)
    {
        Set<String> set=new TreeSet<>();

        if (list==null)
        {
            return;
        }else {
            for (int i=0;i<list.size();i++)
         {
                set.add(list.get(i));
         }

        SharedPreferences sharedPreferences=context.getSharedPreferences
                ("label",Context.MODE_PRIVATE);
        SharedPreferences.Editor ui=sharedPreferences.edit();
        ui.putStringSet("Set",set);
        ui.commit();
    }
    }
    //获取数据
    public List<String> getMyData()
    {
        List<String> lists=new ArrayList<>();
        //参数一：文件名；参数二：文件存储模式
        SharedPreferences sharedPreferences=context.getSharedPreferences
                ("label",Context.MODE_PRIVATE);
        SharedPreferences.Editor ui=sharedPreferences.edit();
        //读取数据
        Set<String> getData=sharedPreferences.getStringSet("Set",null);
        for (String values:getData) {
            lists.add(values);
        }
        //lists.addAll(getData);
        ui.commit();
        return lists;
    }
}
