package com.example.xy.newsday.a.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.bean.Contance;
import com.example.xy.newsday.a.bean.NesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/17.
 */
public class activity_getNews extends AppCompatActivity {
    String urls;
    Context context;
    StringBuffer buffer;
    //创建集合
    RecyclerView recyclerView;
    List<NesData> arrayList=new ArrayList<>();
    public static final int SECCESS=0;//成功
    public static final int FEIALD=1;//失败
    String content;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SECCESS:
                    break;
                case FEIALD:
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_test);
        if (context==null)
        {
            context=this;
        }
        //找到控件
        recyclerView= (RecyclerView) findViewById(R.id.myRecyclers);
        //开启线程
        new Thread(new MyThread()).start();
    }
    //请求网络数据
    public void HttpRequest(String strURL) throws IOException {
        buffer=new StringBuffer();
        URL url=new URL(strURL);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");//设置请求方式
        connection.setRequestProperty("apikey", Contance.BaiduApi);//设置参数头
        final InputStream is=connection.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
        int temp=0;
        while((temp=reader.read())!=-1)
        {
            buffer.append((char)temp);
        }
        reader.close();
        Log.e("activity_getNews", buffer.toString());
        content=buffer.toString();
        //第一层：对象
        try {
            JSONObject jsonObject=new JSONObject(content);
            Log.e("HttpRequest",jsonObject.toString());
            //第二层：对象
            JSONObject jsonObjectOne=new JSONObject(jsonObject.getString("showapi_res_body"));
            Log.e("HttpRequestOne",jsonObjectOne.toString());
            //第三层:对象
            JSONObject jsonObjectSecond=new JSONObject(jsonObjectOne.getString("pagebean"));
            Log.e("HttpRequestSecond",jsonObjectSecond.toString());
            //第四层:数组
            JSONArray jsonArray=new JSONArray(jsonObjectSecond.getString("contentlist"));
            Log.e("HttpRequestArray",jsonArray.toString());
            for (int i=0;i<jsonArray.length();i++)
            {
                jsonObject=new JSONObject(jsonArray.get(i).toString());
                Log.e("activity_jsons", "jsonobject.get(title):" + jsonObject.get("title") );
                    String title = jsonObject.getString("title");
                    Log.e("HttpRequest: ", jsonObject.getString("title"));
                    String content = jsonObject.getString("desc");
                    Log.e("HttpRequestOne", jsonObject.getString("desc"));
                    String data = jsonObject.getString("pubDate");
                    String link = jsonObject.getString("link");
                    Log.e("HttpRequestSecond", jsonObject.getString("pubDate"));
                    String allPages = jsonObject.getString("allPages");
                JSONArray jsonArrays=jsonObject.getJSONArray("imageurls");
                for (int x=0;x<jsonArrays.length();x++) {
                    jsonObject = new JSONObject(jsonArrays.get(x).toString());
                    urls = jsonObject.get("url").toString();
                    Log.e("jsonAply: ", jsonObject.get("url").toString());
                }
                    NesData nesData = new NesData(title, content, data, link, allPages,urls);
                    arrayList.add(nesData);
                Log.e("HttpRequest:io ",arrayList.toString());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //设置布局管理器
                    LinearLayoutManager manager=new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(manager);
                    //创建适配器
                    NewsAdapter adapter=new NewsAdapter(context,arrayList);

                    //Log.e("activity_getNews", "arrayList:" + arrayList);

                    recyclerView.setAdapter(adapter);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("activity_weather", "e:" + e);
        }
    }
    class MyThread implements Runnable{

        @Override
        public void run() {
            try {
                HttpRequest(Contance.BaiDuAdress);
                handler.sendEmptyMessage(SECCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
