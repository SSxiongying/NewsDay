package com.example.xy.newsday.a.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.adapter.SeachAdapter;
import com.example.xy.newsday.a.bean.GetSearchData;
import com.example.xy.newsday.a.bean.NesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xy on 2016/11/15.
 */
public class Fragment_seach extends Fragment{
    EditText editText;
    View view;
    RecyclerView recyclerView;
    String url;
    Button button;
    WebView webview;
    List<NesData> list=new ArrayList<>();
    Handler handler;{
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(manager);
                        SeachAdapter adapter=new SeachAdapter(getContext(),list);
                        //网页的点击事件
                        adapter.setMyOnClicks(new SeachAdapter.MyOnClicks() {
                            @Override
                            public void onClick(int position) {
                                webview.getSettings().setJavaScriptEnabled(true);
                                webview.loadUrl(list.get(position).getLink());
                                Log.e("Fragment_seach", list.get(position).getLink());
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        };
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.activity_fragment_seach,container,false);
        initView();
        return view;
        //查找控件
    }

    private void initView() {
        Log.e("ssa", "ss");
        //查找控件
        webview= (WebView) view.findViewById(R.id.seach_web);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler);
        editText = (EditText) view.findViewById(R.id.txt_seach);
        button = (Button) view.findViewById(R.id.btn_seach);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //新闻查询的点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("YU","UI");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ssss1","op");
                        String NewsData=new GetSearchData().getSeachData(editText.getText().toString());
                        Log.e("ssss2",NewsData);
                        try {
                            JSONObject jsonObject=new JSONObject(NewsData);
                            //第二层：对象
                            JSONObject jsonObjectOne=new JSONObject(jsonObject.getString("showapi_res_body"));
                            Log.e("HttpRequestOne",jsonObjectOne.toString());
                            //第三层:对象
                            JSONObject jsonObjectSecond=new JSONObject(jsonObjectOne.getString("pagebean"));
                            Log.e("HttpRequestSecond",jsonObjectSecond.toString());
                            String allPages=jsonObjectSecond.get("allPages").toString();
                            Log.e("allPages: ",jsonObjectSecond.get("allPages").toString());
                            //第四层:数组
                            JSONArray jsonArray=new JSONArray(jsonObjectSecond.getString("contentlist"));
                            Log.e("jsonAplysss: ",jsonArray.toString());
                            Log.e("HttpRequestArray",jsonArray.length()+"");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                jsonObject=new JSONObject(jsonArray.get(i).toString());
                                String title=jsonObject.getString("title");
                                Log.e("HttpRequest: ", jsonObject.getString("title"));
                                String content = jsonObject.getString("desc");
                                Log.e("HttpRequestOne", jsonObject.getString("desc"));
                                String data = jsonObject.getString("pubDate");
                                Log.e("HttpRequestSecond", jsonObject.getString("pubDate"));
                                String link = jsonObject.getString("link");
                                Log.e("jsonAply: ", jsonObject.getString("link"));
                                JSONArray jsonArrays=jsonObject.getJSONArray("imageurls");
                                for (int x=0;x<jsonArrays.length();x++)
                                {
                                    jsonObject = new JSONObject(jsonArrays.get(x).toString());
                                    url = jsonObject.get("url").toString();
                                    Log.e("jsonAply: ", jsonObject.get("url").toString());
                                }
                                NesData nesdata=new NesData(title,content,data,link,allPages,url);
                                list.add(nesdata);
                                Log.e("pages: ", list.get(i).getAllPages());
                            }
                            handler.sendEmptyMessage(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
