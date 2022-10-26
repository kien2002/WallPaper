package com.example.wallsuper.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallsuper.Entity.WallPaper_Entities;
import com.example.wallsuper.R;
import com.example.wallsuper.interfaces.AdapterCallbackWallpaper;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Custom_adp_home extends RecyclerView.Adapter<Custom_adp_home.ViewHolder> {
    Context context;
    ArrayList<WallPaper_Entities> arrayList;
    ArrayList<WallPaper_Entities> newArrayList;
    AdapterCallbackWallpaper adapterCallbackWallpaper;

    public Custom_adp_home(ArrayList<WallPaper_Entities> arrayList, Context context, AdapterCallbackWallpaper adapterCallbackWallpaper) {
        this.context = context;
        this.arrayList = arrayList;
        newArrayList = new ArrayList<>();
        this.adapterCallbackWallpaper = adapterCallbackWallpaper;
    }

    @Override
    public Custom_adp_home.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_wallpaper, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Custom_adp_home.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(arrayList.get(position).getLink()).into(holder.imageView);
        String cposition = String.valueOf(position);
        String linkImg = arrayList.get(position).getLink();
        Integer wid = Integer.valueOf(arrayList.get(position).getId());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newArrayList.clear();
                int arraySize = arrayList.size();
                int positions = position + 10;
                int increment = 10;
                if (positions == arraySize) {
                    for (int i = position; i < position + increment; i++) {
                        newArrayList.add(new WallPaper_Entities(
                                arrayList.get(i).getId(),
                                arrayList.get(i).getLink())
                        );
                    }
                } else if (positions < arraySize) {
                    for (int i = position; i < positions + increment; i++) {
                        newArrayList.add(new WallPaper_Entities(
                                arrayList.get(i).getId(),
                                arrayList.get(i).getLink())
                        );
                    }
                } else {
                    int sizee = arraySize - position;
                    increment = sizee;
                    for (int i = position; i < positions + increment; i++) {
                        newArrayList.add(new WallPaper_Entities(
                                arrayList.get(i).getId(),
                                arrayList.get(i).getLink())
                        );
                    }
                }
                try {
                    Log.e("NEW DATA", new Gson().toJson(newArrayList));
                    adapterCallbackWallpaper.onWallpaperClicked(cposition, wid, linkImg, newArrayList);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout premium, mainLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.wallPaper);
            premium = itemView.findViewById(R.id.premium);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
