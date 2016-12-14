package com.example.xy.newsday.a.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xy.newsday.R;

/**
 * Created by xy on 2016/11/25.
 */
public class activity_header extends AppCompatActivity{
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headerlayout);
        initView();
    }

    private void initView() {
        //登陆按钮的跳转
        button= (Button) findViewById(R.id.my_btnlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_header.this,activity_login_qq.class);
                startActivity(intent);
            }
        });
    }
}
