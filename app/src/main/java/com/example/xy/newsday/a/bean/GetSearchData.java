package com.example.xy.newsday.a.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by xy on 2016/11/28.
 */
public class GetSearchData {
    public  String getSeachData(String newsName)
    {
        try {
            newsName= URLEncoder.encode(newsName,"UTF-8");
            try {
                URL url=new URL("http://apis.baidu.com/showapi_open_bus/channel_news/search_news?channelName="+newsName);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");//设置请求方式
                httpURLConnection.setRequestProperty("apikey",Contance.BaiduApi);//设置参数头
                InputStream is=httpURLConnection.getInputStream();
                StringBuffer stringBuffer=new StringBuffer();
                //乱码的原因是我们使用的字节流获取数据
                // 所以转换成字符流  但最好是缓冲流
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                int temp=0;
                while ((temp=bufferedReader.read())!=-1)
                {
                    stringBuffer.append((char)temp);
                }
                return new String(stringBuffer.toString().getBytes(),"UTF-8");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newsName;
    }
}
