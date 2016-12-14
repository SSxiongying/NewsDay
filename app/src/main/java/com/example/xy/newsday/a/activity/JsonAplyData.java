package com.example.xy.newsday.a.activity;

import android.util.Log;

import com.example.xy.newsday.a.bean.HotsData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/21.
 */
public class JsonAplyData {
    List<HotsData> hotsDataList=new ArrayList<>();
    public List<HotsData> jsonAplyData(String srt) throws JSONException {
        //第一层：对象
        JSONObject jsonObject=new JSONObject(srt);
        Log.e("jsonAplyDataHot",jsonObject.toString());
        for (int i=0;i<10;i++)
        {
            JSONObject jsonObjectOne=new JSONObject(jsonObject.getString("0"));
            Log.e("jsonAplyDataHotOne",jsonObjectOne.toString());
            String title=jsonObjectOne.get("title").toString();
            Log.e("jsonAplyDataHotSecond",jsonObjectOne.get("title").toString());
            String content=jsonObjectOne.get("description").toString();
            Log.e("jsonAplyDataHotthere",content);
            String data=jsonObjectOne.get("time").toString();
            Log.e("jsonAplyDataHotFour",data);
            //String url=jsonObjectOne.get("url").toString();
            HotsData hotsData=new HotsData(title,content,data);
            hotsDataList.add(hotsData);//将数据加入集合
        }
        return hotsDataList;
    }
}
