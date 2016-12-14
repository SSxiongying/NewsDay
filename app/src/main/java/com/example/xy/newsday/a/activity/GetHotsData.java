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
 * Created by xy on 2016/11/21.
 */
public class GetHotsData {
    static String result=null;
    public static String getHttpHotData(String strUrl,int page) throws IOException {
        StringBuffer buffer=new StringBuffer();
        String strArgs="page="+page;
        Log.e("getHttpHotData: ",strArgs);
        strUrl=strUrl+"?"+strArgs;
        Log.e("strUrl:",strUrl);
        URL url=new URL(strUrl);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");//设置请求方式
        //参数一:建值；参数二：value值
        connection.setRequestProperty("apikey", Contance.BaiduApi);//设置参数头
        InputStream is=connection.getInputStream();
        BufferedReader bufferrader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
        int temp=0;
        while((temp=bufferrader.read())!=-1)
        {
            buffer.append((char)temp);
    }
    bufferrader.close();
        result=buffer.toString();
        return result;
    }
}
