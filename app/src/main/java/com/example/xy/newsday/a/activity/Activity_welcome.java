package com.example.xy.newsday.a.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xy.newsday.R;

/**
 * Created by xy on 2016/11/15.
 */
public class Activity_welcome extends AppCompatActivity {

    private static final int TIME = 5000;
    private static final int GO_HOME=100;
    private static final int GO_GUIDE = 1001;
    private boolean isFirstIn = false;//判断是否是第一次进入。初始值为false
    ImageView images;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        //参数一：操作对象；参数二：指定这个动画要操作这个控件的哪个属性；参数三：
        images= (ImageView) findViewById(R.id.welcome);
        ObjectAnimator animator=ObjectAnimator.ofFloat(images,"alpha",1f,0f,1f);
        //设置动画的时长
        animator.setDuration(5000);
        //开启动画
        animator.start();
    }

    private void init() {
        //执行成功的话，会将数据保存在homeTest.xml文件中
        SharedPreferences preferences=getSharedPreferences("homeTest",MODE_PRIVATE);
        //第一个参数是键值，第二个参数是默认值
        //读取数据
        //读数据
        isFirstIn=preferences.getBoolean("isFirstIn",true);
        if (!isFirstIn)
        {
         //参数一：要做的事情，参数二：时间的长短
            handler.sendEmptyMessageDelayed(GO_HOME,TIME);
        }else {
            handler.sendEmptyMessageDelayed(GO_GUIDE,TIME);
            //存储数据
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
        }
    }
    private void goHome()
    {
        Intent intent=new Intent(Activity_welcome.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void goGuide()
    {
        Intent intent=new Intent(Activity_welcome.this,activity_viewpager.class);
        startActivity(intent);
        finish();
    }
}
