package com.example.xy.newsday.a.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.bean.NesData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xy on 2016/11/17.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int Header=0;//头部的布局
    public static final int Content=1;//内容分的布局
    private LayoutInflater inflater;
    Context context;
    List<NesData> arrayLisy;
    private MyOnClick myOnClicks;

    public NewsAdapter(Context context, List<NesData> arrayLisy) {
        this.context = context;
        this.arrayLisy = arrayLisy;
        inflater=LayoutInflater.from(context);
    }
    //根据item的位置确定加载的布局
    @Override
    public int getItemViewType(int position) {
        if (position==0)
        {
            return Header;
        }else
        {
            return Content;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //加载控件
        TextView title;
        TextView content;
        TextView data;
        ImageView images;

        public ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.titles);
            content= (TextView) itemView.findViewById(R.id.content);
            data= (TextView) itemView.findViewById(R.id.timess);
            images= (ImageView) itemView.findViewById(R.id.imagess);
        }
    }
    //item点击事件
    public interface MyOnClick
    {
        void onClick(int position);
    }
    public void  setMyOnClicks(MyOnClick myOnClicks)
    {
        this.myOnClicks=myOnClicks;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        if (viewType==Header)
        {
            View view=inflater.inflate(R.layout.activity_viewheader_item,parent,false);
            ViewHaderHolder viewHaderHolder=new ViewHaderHolder(view);
            return viewHaderHolder;
        }else
        {
            View view=inflater.inflate(R.layout.activity_viewpagertest_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHaderHolder)
        {
            Picasso.with(context).load(arrayLisy.get(0).getUrl()).
                    into(((ViewHaderHolder) holder).imageHeader);
            ((ViewHaderHolder) holder).textView_bottom.
                    setText(arrayLisy.get(position).getTitle());
        }else if(holder instanceof ViewHolder)
        {
            //设置内容
            //picasso的使用
            Picasso.with(context).load(arrayLisy.get(position).getUrl()).into(((ViewHolder) holder).images);
            ((ViewHolder) holder).title.setText(arrayLisy.get(position).getTitle());
            ((ViewHolder) holder).content.setText(arrayLisy.get(position).getContent());
            ((ViewHolder) holder).data.setText(arrayLisy.get(position).getData());
            if (myOnClicks!=null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myOnClicks.onClick(position);
                    }
                });
            }
        }
    }

    //返回item的长度
    @Override
    public int getItemCount() {
        return arrayLisy.size();
    }

    private class ViewHaderHolder extends RecyclerView.ViewHolder{
        ImageView imageHeader;
        TextView textView_bottom;
        public ViewHaderHolder(View itemView) {
            super(itemView);
            imageHeader= (ImageView) itemView.findViewById(R.id.image_header);
            textView_bottom= (TextView) itemView.findViewById(R.id.text_bottom);
        }
    }
}
