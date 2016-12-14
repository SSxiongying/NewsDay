package com.example.xy.newsday.a.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.fragment.Fragment_hots;
import com.example.xy.newsday.a.fragment.Fragment_news;
import com.example.xy.newsday.a.fragment.Fragment_seach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

public class MainActivity extends AppCompatActivity{
    //声明fragment
    Fragment newsFragment=null;
    Fragment hotsFragment=null;
    Fragment seachFragment=null;
    FragmentManager fragmentmanager=null;
    //声明GirdView
    GridView gridView;
    gridviewAdapter adapter;//声明适配器
    List<newsInfo> list;
    List<String> nesInfo;
    //头部的登陆按钮
    Button btn_login;
    ImageView image_food;
    ImageView image_hot;
    ImageView image_search;
    //声明toolBar与侧滑栏
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ImageView imageView_share;
    fragment_aboutme fragment_aboutme=new fragment_aboutme();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nesInfo=new ArrayList<>();
        //分享
        imageView_share= (ImageView) findViewById(R.id.share);
        imageView_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
        fragmentmanager=getSupportFragmentManager();
        initView();
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
    private void initView() {
        image_food= (ImageView) findViewById(R.id.food_img);
        image_hot= (ImageView) findViewById(R.id.hot_image);
        //美食
        image_food.setOnClickListener(new MyOnClick_Food());
        //最热菜肴
        image_hot.setOnClickListener(new MyOnClick_Hot());
        image_search= (ImageView) findViewById(R.id.search_image);
        //搜索菜肴
        image_search.setOnClickListener(new MyOnClick_Search());
        //initData();//加载数据
        initToolBar();
        //创建适配器
        //final Menu menu= (Menu) findViewById(R.id.abouveme);
//        adapter=new gridviewAdapter(this,list);
//        gridView= (GridView) findViewById(R.id.gridview);
//        gridView.setAdapter(adapter);//设置适配器
//        //设置gridView的点击事件
//        gridView.setOnItemClickListener(new MyOnItemClickListenner());
        NavigationView navigationView= (NavigationView) findViewById(R.id.navigative);
        //加载头部布局
        View view=navigationView.inflateHeaderView(R.layout.headerlayout);
        btn_login= (Button) view.findViewById(R.id.my_btnlogin);
        //头部登陆的点击
        btn_login.setOnClickListener(new myOnclick());
        //创建navigationView中item的监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.abouveme:
                        //详情页面的跳转
                        Intent intent=new Intent(MainActivity.this,acticity_aboveme.class);
                        startActivity(intent);
                        item.setCheckable(true);
                        break;
                    case R.id.save:
                        //收藏页面的跳转
                        Intent intentOne=new Intent(MainActivity.this,Activity_sava.class);
                        startActivity(intentOne);
                        item.setCheckable(true);

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initToolBar() {
        drawerLayout= (DrawerLayout) findViewById(R.id.my_drawers);
        toolbar= (Toolbar) findViewById(R.id.myToolbar);
        toolbar.setTitle("NewsDay");//设置标题
        toolbar.setTitleTextColor(Color.WHITE);//标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    //数据
//    private void initData()
//    {
//        list=new ArrayList<>();
//        //添加数据
//        list.add(new newsInfo(R.drawable.new_unselected,"咨询"));
//        list.add(new newsInfo(R.drawable.collect_unselected,"热点"));
//        list.add(new newsInfo(R.drawable.find_defult,"搜索"));
//    }

    private class myOnclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            platfrom();
        }
    }
    //qq的登陆
    public void platfrom()
    {
        ShareSDK.initSDK(this);
        Platform qq= ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //输出所有授权信息
                //调用weibo.getDb().getWeiboId()请求用户在此平台上的ID
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
        qq.SSOSetting(false);
        qq.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }

    private class MyOnClick_Food implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            image_food.setImageResource(R.drawable.new_selected);
            if (newsFragment==null)
            {
                newsFragment=new Fragment_news();
            }
            //开启一个事务
            FragmentTransaction action=fragmentmanager.beginTransaction();
            //替换为当前的fragment
            action.replace(R.id.id_content,newsFragment);
            action.commit();

        }
    }

    private class MyOnClick_Hot implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            image_hot.setImageResource(R.drawable.collect_selected);
            if (hotsFragment==null)
            {
                hotsFragment=new Fragment_hots();
            }
            //开启一个事务
            FragmentTransaction actionhots=fragmentmanager.beginTransaction();
            //替换为当前的fragment
            actionhots.replace(R.id.id_content,hotsFragment);
            actionhots.commit();
        }
    }

    private class MyOnClick_Search implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            image_search.setImageResource(R.drawable.find_selected);
            if (seachFragment==null)
            {
                seachFragment=new Fragment_seach();
            }
            //开启一个事务
            FragmentTransaction actionseach=fragmentmanager.beginTransaction();
            //替换为当前的fragment
            actionseach.replace(R.id.id_content,seachFragment);
            actionseach.commit();
        }
    }
}
