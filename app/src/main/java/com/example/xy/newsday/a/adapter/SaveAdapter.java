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
import com.example.xy.newsday.a.bean.NesData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xy on 2016/11/27.
 */
public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHolef> {

    Context context;
    List<NesData> list;
    LayoutInflater inflater;

    public SaveAdapter(Context context, List<NesData> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    public class ViewHolef extends RecyclerView.ViewHolder {
        TextView titles;
        TextView Content;
        TextView Data;
        ImageView images;
    public ViewHolef(View itemView) {
        super(itemView);
        titles= (TextView) itemView.findViewById(R.id.titless_scc);
        Content= (TextView) itemView.findViewById(R.id.content_scc);
        Data= (TextView) itemView.findViewById(R.id.timess_scc);
        images= (ImageView) itemView.findViewById(R.id.imagess_sccc);
    }
}
    @Override
    public ViewHolef onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_shocaniitem_,null);
        ViewHolef viewHolef=new ViewHolef(view);
        return viewHolef;
    }

    @Override
    public void onBindViewHolder(ViewHolef holder, int position) {
        Picasso.with(context).load(list.get(position).getUrl()).into(holder.images);
        holder.titles.setText(list.get(position).getTitle());
        Log.e("SaveAdapterData", list.get(position).getData());
        holder.Content.setText(list.get(position).getContent());
        holder.Data.setText(list.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
