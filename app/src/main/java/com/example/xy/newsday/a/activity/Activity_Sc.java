package com.example.xy.newsday.a.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.SQL.MySqliteHelper;
import com.example.xy.newsday.a.bean.NesData;
import com.example.xy.newsday.a.fragment.CurrentFragment;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by xy on 2016/11/22.
 */
public class Activity_Sc extends AppCompatActivity {
    WebView webView;
    ImageView imageView;
    List<String> list=new ArrayList<>();
    String imgUrl;
    String link=null;
    ImageView image;
    ImageView images;
    Context context;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_link);
        if (context==null)
        {
            context=this;
        }
        images= (ImageView) findViewById(R.id.link_return);
        image= (ImageView) findViewById(R.id.share_link);
        //返回按钮的监听
        images.setOnClickListener(new OnClikReturn());
        //分享功能的监听
        image.setOnClickListener(new OnClickshare());
        final MySqliteHelper mySqliteHelper=new MySqliteHelper(context);
        Intent intent=getIntent();
        final List<String> stringList=intent.getStringArrayListExtra("link");
        link=stringList.get(1);
        imgUrl=stringList.get(2);

        //加载控件
        webView= (WebView) findViewById(R.id.myWebView);
        //加载网页
        webView.loadUrl(link);
        Log.e("onCreate: ",link);

        //找到收藏控件
        imageView= (ImageView) findViewById(R.id.myShouChang);
        //实现点击收藏的功能

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(webView,"收藏成功",Snackbar.LENGTH_LONG).show();
                if (mySqliteHelper.chakan(stringList.get(0))==0)
                {
                    mySqliteHelper.addUser(stringList.get(0),stringList.get(1),stringList.get(2),stringList.get(3),stringList.get(4));
                    Snackbar.make(webView,"收藏成功",Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(webView,"已经收藏！",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(link);
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setImagePath(imgUrl);
        oks.setUrl(link);
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(link);

// 启动分享GUI
        oks.show(this);
    }

    private class OnClikReturn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Activity_Sc.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private class OnClickshare implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showShare();
        }
    }
}
