package com.example.xy.newsday.a.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xy.newsday.R;
import com.example.xy.newsday.a.bean.HotsData;
import com.example.xy.newsday.a.bean.NesData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xy on 2016/11/21.
 */
public class HosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int Header=0;
    public static final int Content=1;
    Context context;
    List<NesData> list;
    LayoutInflater inflater;
    private MyOnClicks myOnClicks;

    public HosAdapter(Context context, List<NesData> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

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
    public interface MyOnClicks
    {
        void onClick(int position);
    }
    public void  setMyOnClicks(MyOnClicks myOnClicks)
    {
        this.myOnClicks=myOnClicks;
    }
    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView content;
        TextView data;
        ImageView imageViews;
        public ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.titless);
            content= (TextView) itemView.findViewById(R.id.content_hot);
            data= (TextView) itemView.findViewById(R.id.timess_hot);
            imageViews= (ImageView) itemView.findViewById(R.id.imagess_hot);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==Header)
        {
            View view=inflater.inflate(R.layout.activity_headerhot_item,parent,false);
            ViewHaderHolder viewHaderHolder=new ViewHaderHolder(view);
            return viewHaderHolder;
        }else
        {
            View view=inflater.inflate(R.layout.activity_viewpagerhot_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHaderHolder)
        {
            Picasso.with(context).load(list.get(position).getUrl()).into(((ViewHaderHolder) holder).imageHeaders);
            ((ViewHaderHolder) holder).textViews.setText(list.get(position).getTitle());
        }else if (holder instanceof ViewHolder)
        {
            //设置数据
            Picasso.with(context).load(list.get(position).getUrl()).into(((ViewHolder) holder).imageViews);
            ((ViewHolder) holder).title.setText(list.get(position).getTitle());
            Log.e("HosAdapter", list.get(position).getTitle());
            ((ViewHolder) holder).content.setText(list.get(position).getContent());
            ((ViewHolder) holder).data.setText(list.get(position).getData());
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHaderHolder extends RecyclerView.ViewHolder{
        ImageView imageHeaders;
        TextView textViews;
        public ViewHaderHolder(View itemView) {
            super(itemView);
            imageHeaders= (ImageView) itemView.findViewById(R.id.image_headers);
            textViews= (TextView) itemView.findViewById(R.id.text_bottomss);
        }
    }
}
