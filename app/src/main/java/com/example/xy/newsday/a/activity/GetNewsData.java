package com.example.xy.newsday.a.activity;

import android.util.Log;

import com.example.xy.newsday.a.bean.Contance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xy on 2016/11/17.
 */
public class GetNewsData {
    static String result=null;
    public static String getHttpDatas(String strURL, String name,int page) throws IOException {
        StringBuffer buffer=new StringBuffer();
        String httpArg="channelId="+name+"&page="+page;
        Log.e("getHttpDatas: ",httpArg);
        strURL=strURL+"?"+httpArg;
        Log.e("GetNewsData", strURL.toString());
        URL url=new URL(strURL);
        Log.e("GetNewsData", "1");
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        Log.e("GetNewsData", "1");
        connection.setRequestMethod("GET");//设置请求方式
        connection.setRequestProperty("apikey", Contance.BaiduApi);//设置参数头
        Log.e("GetNewsData", "1");
        InputStream is=connection.getInputStream();
        Log.e("GetNewsData", "1");
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
        int temp=0;
        while((temp=reader.read())!=-1)
        {
            buffer.append((char)temp);
        }
        Log.e("GetNewsData", "1");
        Log.e("GetNewsData", buffer.toString());
        reader.close();
        result=buffer.toString();
        return result;//你返回什么？
    }
}
