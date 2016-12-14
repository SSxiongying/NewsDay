package com.example.xy.newsday.a.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.xy.newsday.R;

/**
 * Created by xy on 2016/11/23.
 */
public class acticity_aboveme extends AppCompatActivity {

    Toolbar mytoolBar;
    ImageView images;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_aboutme);
        initView();
    }

    private void initView() {
        mytoolBar= (Toolbar) findViewById(R.id.myToolbars_abouve);
        images= (ImageView) findViewById(R.id.returns);
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(acticity_aboveme.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
