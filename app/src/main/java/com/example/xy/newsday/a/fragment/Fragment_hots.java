package com.example.xy.newsday.a.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.activity.GetHotsData;
import com.example.xy.newsday.a.activity.GetNewsData;
import com.example.xy.newsday.a.activity.JsonAplay;
import com.example.xy.newsday.a.activity.JsonAplyData;
import com.example.xy.newsday.a.activity.NewsAdapter;
import com.example.xy.newsday.a.adapter.HosAdapter;
import com.example.xy.newsday.a.bean.Contance;
import com.example.xy.newsday.a.bean.GetHttpDataSecond;
import com.example.xy.newsday.a.bean.HotsData;
import com.example.xy.newsday.a.bean.NesData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/15.
 */
public class Fragment_hots extends Fragment {
    RecyclerView myhotsRecycler;
    //HosAdapter hosAdapter;
    WebView webview_hots;
    SwipeRefreshLayout swipRefresh;
    String str;
    int lastPosition=0;
    int page=1;
    View view=null;
    List<NesData> list=new ArrayList<>();
   Handler handler;{
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 1:
                        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        myhotsRecycler.setLayoutManager(manager);
                        final HosAdapter hosAdapter=new HosAdapter(getContext(),list);
                        //网页的点击事件
                        hosAdapter.setMyOnClicks(new HosAdapter.MyOnClicks() {
                            @Override
                            public void onClick(int position) {
                                webview_hots.getSettings().setJavaScriptEnabled(true);
                                //加载网页地址
                                webview_hots.loadUrl(list.get(position).getLink());
                                Log.e("onClick: ",list.get(position).getLink());
                            }
                        });
                        myhotsRecycler.setAdapter(hosAdapter);
                        break;
                    case 2://下拉刷新
                        swipRefresh.setRefreshing(false);
                        break;
                    case 3://上拉刷新
                        LinearLayoutManager managers=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        myhotsRecycler.setLayoutManager(managers);
                        HosAdapter adapter=new HosAdapter(getContext(),list);
                        //刷新item的点击事件
                        adapter.setMyOnClicks(new HosAdapter.MyOnClicks() {
                            @Override
                            public void onClick(int position) {
                                webview_hots.getSettings().setJavaScriptEnabled(true);
                                webview_hots.loadUrl(list.get(position).getLink());
                                Log.e("onClick: ",list.get(position).getLink());
                            }
                        });
                        myhotsRecycler.setAdapter(adapter);
                        swipRefresh.setRefreshing(false);
                        break;
                }
            }
        };
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_fragment_hos,container,false);
        initView();
        return view;
    }

    private void initView() {
        webview_hots= (WebView) view.findViewById(R.id.myWebView_hots);
        swipRefresh= (SwipeRefreshLayout) view.findViewById(R.id.swip_hot);
        myhotsRecycler= (RecyclerView) view.findViewById(R.id.myRecyclers_hot);
        //设置刷新时的颜色
        swipRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_red_light,
                android.R.color.holo_orange_light,android.R.color.holo_green_light);
        //第一次进入页面时显示加载进度条
        swipRefresh.setProgressViewOffset(false,0, (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        swipRefresh.setOnRefreshListener(new OnRreshLayout());
        //设置刷新状态
        swipRefresh.setRefreshing(false);
        final LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        final NewsAdapter newsAdapter=new NewsAdapter(getContext(),list);
        Log.e("Fragment_hots", list.toString());
        //上拉刷新
        myhotsRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 正在滑动的状态
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断滑动状态；判断是否是最后一个
                if (newState==recyclerView.SCROLL_STATE_IDLE&&lastPosition+1==newsAdapter.getItemCount())
                {
                    swipRefresh.setRefreshing(true);
                    page++;
                    //加载数据的方法
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                try {
                                    //解析数据
                                    str = GetHotsData.getHttpHotData(Contance.BaiDuAdress,page);
                                    JsonAplay jsonAplay = new JsonAplay();
                                    list.clear();
                                    //获取数据
                                    try {
                                        list = jsonAplay.jsonAply(str);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    handler.sendEmptyMessage(3);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }

            /**
             * 滑动结束
             * @param recyclerView
             * @param dx
             * @param dy
             * 获取最后item的位置：判断
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滑动监听
                super.onScrolled(recyclerView, dx, dy);
                lastPosition=manager.findLastVisibleItemPosition();//通过recycler的manager来获取最后的位置
            }
        });
    }
    //加载数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str= GetHotsData.getHttpHotData(Contance.BaiDuAdress,page);
                    Log.e("runs",str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonAplay jsonArray=new JsonAplay();
                try {
                    list.clear();
                    list=jsonArray.jsonAply(str);
                    handler.sendEmptyMessage(1);
                    Log.e("run: ","list.size():"+list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public class OnRreshLayout implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        handler.sendEmptyMessage(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
