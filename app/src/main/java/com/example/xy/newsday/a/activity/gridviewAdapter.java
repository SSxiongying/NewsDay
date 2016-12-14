package com.example.xy.newsday.a.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xy.newsday.R;

import java.util.List;

import static com.example.xy.newsday.R.id.line1;
import static com.example.xy.newsday.R.id.text_new;

/**
 * Created by xy on 2016/11/15.
 */
public class gridviewAdapter extends BaseAdapter {

    Context context;
    List<newsInfo> list;
    LayoutInflater inflater;

    public gridviewAdapter(Context context, List<newsInfo> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //加载布局
        view=this.inflater.inflate(R.layout.activity_item,null);
        //查找控件
        ImageView imageView= (ImageView) view.findViewById(R.id.imag_news);
        TextView textView= (TextView) view.findViewById(R.id.text_new);
        //设置数据
        imageView.setImageResource(list.get(i).getIcon());
        textView.setText(list.get(i).getIconName());
        return view;
    }
}
