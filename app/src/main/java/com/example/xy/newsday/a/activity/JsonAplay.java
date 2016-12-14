package com.example.xy.newsday.a.activity;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.xy.newsday.a.bean.NesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/17.
 */
public class JsonAplay {
    List<NesData> arrayList=new ArrayList<>();
    String url;
    public List<NesData> jsonAply(String srt) throws JSONException {
        JSONObject jsonObject=new JSONObject(srt);
        Log.e("JsonAplayoppp", srt);
        Log.e("HttpRequest",jsonObject.toString());
        //第二层：对象
        JSONObject jsonObjectOne=new JSONObject(jsonObject.getString("showapi_res_body"));
        Log.e("HttpRequestOne",jsonObjectOne.toString());
        //第三层:对象
        JSONObject jsonObjectSecond=new JSONObject(jsonObjectOne.getString("pagebean"));
        Log.e("HttpRequestSecond",jsonObjectSecond.toString());
        String allPages=jsonObjectSecond.get("allPages").toString();
        Log.e("allPages: ",jsonObjectSecond.get("allPages").toString());
        //第四层:数组
        JSONArray jsonArray=new JSONArray(jsonObjectSecond.getString("contentlist"));
        Log.e("jsonAplysss: ",jsonArray.toString());
        Log.e("HttpRequestArray",jsonArray.length()+"");
        for (int i=0;i<jsonArray.length();i++)
        {
            jsonObject=new JSONObject(jsonArray.get(i).toString());
                String title = jsonObject.getString("title");
                Log.e("HttpRequest: ", jsonObject.getString("title"));
                String content = jsonObject.getString("desc");
                Log.e("HttpRequestOne", jsonObject.getString("desc"));
                String data = jsonObject.getString("pubDate");
                Log.e("HttpRequestSecond", jsonObject.getString("pubDate"));
                String link = jsonObject.getString("link");
                Log.e("jsonAply: ", jsonObject.getString("link"));
            JSONArray jsonArrays=jsonObject.getJSONArray("imageurls");
            for (int x=0;x<jsonArrays.length();x++) {
                jsonObject = new JSONObject(jsonArrays.get(x).toString());
                url = jsonObject.get("url").toString();
                Log.e("jsonAply: ", jsonObject.get("url").toString());
            }
                NesData nesData = new NesData(title, content, data, link, allPages,url);
                arrayList.add(nesData);
                Log.e("pages: ", arrayList.get(i).getAllPages());
        }
        Log.e("JsonAplay", "arrayList.size():" + arrayList.size());
        return arrayList;
    }
}
