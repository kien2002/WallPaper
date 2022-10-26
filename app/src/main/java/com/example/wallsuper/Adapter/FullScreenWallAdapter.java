package com.example.wallsuper.Adapter;

import com.bumptech.glide.Glide;
import com.example.wallsuper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.wallsuper.Entity.WallPaper_Entities;

import java.util.ArrayList;

public class FullScreenWallAdapter extends PagerAdapter {
    Context context;
    ArrayList<WallPaper_Entities> arrayList;
    String linkImg;
    Integer wid;
    ImageView imageViewSlider;

    public FullScreenWallAdapter(Context context, ArrayList<WallPaper_Entities> arrayListWall, String linkImg, Integer wid) {
        this.context = context;
        this.arrayList = arrayListWall;
        this.linkImg = linkImg;
        this.wid = wid;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.view_wallpaper_adapter, container, false);
        imageViewSlider = view.findViewById(R.id.imgSlider);
        Glide.with(context).load(arrayList.get(position).getLink()).into(imageViewSlider);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
