package com.example.xy.newsday.a.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xy.newsday.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.ShareSDKR;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by xy on 2016/11/25.
 */
public class activity_login_qq extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        button= (Button) findViewById(R.id.btn_qqlogin);
        //登陆按钮的监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //platfrom();
            }
        });
    }
    //手动授权

    public void platfrom(){

        Platform qq= ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //输出所有授权信息
                platform.getDb().exportData();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qq.authorize();//单独授权,OnComplete返回的hashmap是空的

        qq.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }
}
