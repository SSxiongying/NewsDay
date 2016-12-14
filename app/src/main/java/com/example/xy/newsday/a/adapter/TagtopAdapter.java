package com.example.xy.newsday.a.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xy.newsday.R;

import java.util.List;

/**
 * Created by xy on 2016/11/22.
 */
public class TagtopAdapter extends RecyclerView.Adapter<TagtopAdapter.ViewHolder>{

    Context context;
    List<String> list;
    LayoutInflater inflater;
    private MyOnClick myOnClicks;

    public TagtopAdapter(Context context, List<String> list) {
        this.context=context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewss;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    //创建回调
    public interface MyOnClick {
        void onClick(int position);
    }

    public void  setMyOnClicks(MyOnClick myOnClicks)
    {
        this.myOnClicks=myOnClicks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_tagtop_tem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.textViewss= (TextView) view.findViewById(R.id.myText);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //设置数据
        holder.textViewss.setText(list.get(position));
        //判断监听
        if (myOnClicks!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myOnClicks.onClick(position);//点击事件
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
