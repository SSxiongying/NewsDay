package com.example.xy.newsday.a.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.SQL.MySqliteHelper;
import com.example.xy.newsday.a.adapter.SaveAdapter;
import com.example.xy.newsday.a.adapter.SolectAdapter;
import com.example.xy.newsday.a.bean.NesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/23.
 */
public class Activity_sava extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    Toolbar toolbar;
    ImageView image_save;
    ArrayList<NesData> as;
    MySqliteHelper mySqliteHelper;
    WebView webView;
    SolectAdapter adapter;
    Button btn_delete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_shoucang);
        if (context==null)
        {
            context=this;
        }
        toolbar= (Toolbar) findViewById(R.id.save_toobar);
        image_save= (ImageView) findViewById(R.id.lable_save);//返回按钮的监听
        image_save.setOnClickListener(new SaveMyClick());
        recyclerView= (RecyclerView) findViewById(R.id.sc_recycler); //初始化控件
        //设置布局样式
        LinearLayoutManager manager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        //获取数据
        mySqliteHelper=new MySqliteHelper(context);
        as=mySqliteHelper.fandUser();
        adapter=new SolectAdapter(context,as);//创建适配器
        //网页的点击事件
        adapter.setMyOnClicks(new SolectAdapter.MyOnClicks() {
            @Override
            public void onClick(int position) {
                Log.e("Activity_sava", as.get(position).getTitle());
                as.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class SaveMyClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Activity_sava.this,MainActivity.class);
            startActivity(intent);
        }
    }
    public void dialog()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("你确定要删除这条新闻吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //as.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消",null).show();
        builder.create().show();
    }
}
