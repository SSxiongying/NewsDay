package com.example.xy.newsday.a.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
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
import com.example.xy.newsday.a.activity.Activity_Sc;
import com.example.xy.newsday.a.activity.GetNewsData;
import com.example.xy.newsday.a.activity.JsonAplay;
import com.example.xy.newsday.a.activity.NewsAdapter;
import com.example.xy.newsday.a.bean.Contance;
import com.example.xy.newsday.a.bean.GetHttpDataSecond;
import com.example.xy.newsday.a.bean.NesData;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/15.
 */
public class CurrentFragment extends Fragment {
    String str=null;
    String data=null;
    int page=1;
    View view;
    List<NesData> list=new ArrayList<>();
    ArrayList<NesData> arrayList=new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    WebView myWebView;
    Context context=getContext();
    int lastPosition=0;
    Handler handler;
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        final NewsAdapter newsAdapter = new NewsAdapter(getActivity(), list);
                        //设置item的监听事件
                        newsAdapter.setMyOnClicks(new NewsAdapter.MyOnClick() {
                            @Override
                            public void onClick(int position) {
                                List<String> lists=new ArrayList<String>();
                                lists.add(list.get(position).getTitle());
                                lists.add(list.get(position).getLink());
                                lists.add(list.get(position).getUrl());
                                lists.add(list.get(position).getData());
                                lists.add(list.get(position).getContent());
                                Intent intent=new Intent(getActivity(),Activity_Sc.class);
                                intent.putStringArrayListExtra("link", (ArrayList<String>) lists);
                                //intent.putExtra("link",(list.get(position).getLink()));
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(newsAdapter);
                        break;
                    case 2://下拉刷新
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    case 3://上拉刷新
                       LinearLayoutManager managers=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(managers);
                        NewsAdapter adapter=new NewsAdapter(getContext(),arrayList);
                        //刷新item的点击事件
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_fragment_currentfragment,container,false);
        initView();
        return view;
    }

    private void initView() {
        //初始化控件
        myWebView= (WebView) view.findViewById(R.id.myWebView);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.mySwipeRefresh);
        recyclerView= (RecyclerView) view.findViewById(R.id.myRecyclersss);
        final int spacingInPixels=getResources().getDimensionPixelOffset(R.dimen.space);

        recyclerView.addItemDecoration(new SpaceItemDecoretion(spacingInPixels));
        //设置刷新时的颜色
       swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_red_light,
                android.R.color.holo_orange_light,android.R.color.holo_green_light);
        //第一次进入页面时显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false,0, (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new OnRreshLayout());
        //设置刷新状态
        swipeRefreshLayout.setRefreshing(false);
        final LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        final NewsAdapter newsAdapter=new NewsAdapter(getContext(),list);
        //上拉刷新
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    swipeRefreshLayout.setRefreshing(true);
                    page++;
                    //加载数据的方法
                    new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        //重新获取数据
                                        Bundle bundle = getArguments();
                                        data = bundle.getString("title");//通过键值，获取相应的内容
                                        GetHttpDataSecond getHttpData = new GetHttpDataSecond();
                                        try {
                                            //解析数据
                                            data = getHttpData.getHttpDatass(Contance.BaiDuAdress,data,page);
                                            JsonAplay jsonAplay = new JsonAplay();
                                            arrayList.clear();
                                            //获取数据
                                            try {
                                                arrayList = (ArrayList<NesData>) jsonAplay.jsonAply(data);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            newsAdapter.setMyOnClicks(new NewsAdapter.MyOnClick() {
                                                @Override
                                                public void onClick(int position) {
                                                    myWebView.loadUrl(arrayList.get(position).getLink());
                                                }
                                            });
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle=getArguments();
        str=bundle.getString("title");//通过键值，获取相应的内容
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetNewsData getNewsData=new GetNewsData();
                try {
                    str=getNewsData.getHttpDatas(Contance.BaiDuAdress,str,page);
                    Log.e("CurrentFragment", str);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("CurrentFragment", e.toString());
                }
                Log.e("CurrentFragment99999", str);
                JsonAplay jsonAplay=new JsonAplay();
                try {
                    list.clear();
                    list=jsonAplay.jsonAply(str);
                    Log.e("CurrentFragment", "list.size():" + list.size());
                    handler.sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("CurrentFragment00000", e.toString());
                }
            }
        }).start();
    }
    //下拉刷新的实现
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
    public class SpaceItemDecoretion extends RecyclerView.ItemDecoration
    {

        private int space;

        public SpaceItemDecoretion(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildPosition(view)!=0)
            {
                outRect.top=space;
            }
        }
    }

}
