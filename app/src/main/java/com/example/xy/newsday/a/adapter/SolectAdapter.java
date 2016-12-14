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
import com.example.xy.newsday.a.SQL.MySqliteHelper;
import com.example.xy.newsday.a.bean.NesData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xy on 2016/11/25.
 */
public class SolectAdapter extends RecyclerView.Adapter<SolectAdapter.ViewHolder> {

    Context context;
    List<NesData> as;
    LayoutInflater inflater;
    private MyOnClicks myOnClicks;

    public SolectAdapter(Context context, List<NesData> as) {
        this.context = context;
        this.as = as;
        Log.d("SolectAdapterss: ","iop");
        inflater=LayoutInflater.from(context);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titles;
        TextView Content;
        TextView Data;
        ImageView images;

        public ViewHolder(View itemView) {
            super(itemView);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View view=inflater.inflate(R.layout.activity_shocaniitem_,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        //查找控件
        viewHolder.titles= (TextView) view.findViewById(R.id.titless_scc);
        viewHolder.Content= (TextView) view.findViewById(R.id.content_scc);
        viewHolder.Data= (TextView) view.findViewById(R.id.timess_scc);
        viewHolder.images= (ImageView) view.findViewById(R.id.imagess_sccc);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //加载数据
        Picasso.with(context).load(as.get(position).getUrl()).into(holder.images);
        holder.titles.setText(as.get(position).getTitle());
        Log.e("SolectAdapter", as.get(position).getContent());
        holder.Content.setText(as.get(position).getContent());
        holder.Data.setText(as.get(position).getData());
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
        return as.size();
    }

}
