package com.example.xy.newsday.a.bean;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/19.
 */
public class GetHttpDataSecond {
    static String result=null;
    public String getHttpDatass(String strURL, String name,int page) throws IOException {
        StringBuffer buffer = new StringBuffer();
        Log.e("getHttpDatass: ", String.valueOf(page));
        String httpArg="channelId="+name+"&page="+(page);
        Log.e("getHttpDatas: ",httpArg);
        strURL=strURL+"?"+httpArg;
        Log.e("GetNewsData", strURL.toString());
        URL url=new URL(strURL);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");//设置请求方式
        connection.setRequestProperty("apikey", Contance.BaiduApi);//设置参数头
        InputStream is=connection.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
        int temp=0;
        while((temp=reader.read())!=-1)
        {
            buffer.append((char)temp);
        }
        reader.close();
        result=buffer.toString();
        return result;
    }
}
