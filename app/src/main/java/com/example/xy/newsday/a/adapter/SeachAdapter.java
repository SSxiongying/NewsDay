package com.example.xy.newsday.a.adapter;

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
 * Created by xy on 2016/11/28.
 */
public class SeachAdapter extends RecyclerView.Adapter<SeachAdapter.ViewHolder> {
    private LayoutInflater inflater;
    List<NesData> list;
    Context context;
    private MyOnClicks myOnClicks;

    public SeachAdapter(Context context, List<NesData> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titles;
        TextView content;
        TextView data;
        ImageView imags;
        public ViewHolder(View itemView) {
            super(itemView);
            titles= (TextView) itemView.findViewById(R.id.titless_seach);
            content= (TextView) itemView.findViewById(R.id.content_seach);
            data= (TextView) itemView.findViewById(R.id.timess_seach);
            imags= (ImageView) itemView.findViewById(R.id.imagess_seach);
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
    @Override
    public SeachAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_seach_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SeachAdapter.ViewHolder holder, final int position) {
        //设置数据
        Picasso.with(context).load(list.get(position).getUrl()).into(holder.imags);
        holder.titles.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.data.setText(list.get(position).getData());
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

    @Override
    public int getItemCount() {
        return list.size();
    }

}
