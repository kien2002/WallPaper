package com.example.wallsuper.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallsuper.EditImagePreview;
import com.example.wallsuper.FullScreenWallper;
import com.example.wallsuper.R;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Activity activity;
    List<File> stringList;
    String arrayType;

    public ImageAdapter(Activity activity, List<File> stringList, String arrayType) {
        this.activity = activity;
        this.stringList = stringList;
        this.arrayType = arrayType;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_wallpaper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(activity).load(stringList.get(position).toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.wallpaper);

        holder.wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditImagePreview.class);
                intent.putExtra("pos", position);
                intent.putExtra("path", "1");
                intent.putExtra("type", arrayType);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wallpaper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaper = itemView.findViewById(R.id.wallPaper);
        }
    }
}
