  package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder>
{
    Context context;
    List<Articles> list;
    Animation from_left;
    public MyAdapter(Context context,List<Articles> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.listitems,parent,false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        from_left = AnimationUtils.loadAnimation(context,R.anim.from_left);
        holder.title.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(list.get(position).getUrlToImage())
                .placeholder(R.drawable.news_thumbnail)
                .into(holder.img);

        final String NewsUrl = list.get(position).getUrl();
        holder.cardView.setAnimation(from_left);
        holder.title.setAnimation(from_left);
        holder.publishedAt.setAnimation(from_left);
        holder.publishedAt.setText(list.get(position).getPublishedAt().replace("T"," Time ").replace("Z",""));
        holder.title.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("URL", NewsUrl);
            intent.setClass(context, BrowseWeb.class);
            context.startActivity(intent);
        });
        holder.img.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("URL", NewsUrl);
            intent.setClass(context, BrowseWeb.class);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView title,publishedAt;
        ImageView img;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            img = itemView.findViewById(R.id.imageViewThumbnail);
            cardView = itemView.findViewById(R.id.MaincardView);
            publishedAt = itemView.findViewById(R.id.textViewPublishedDate);
        }
    }
}
