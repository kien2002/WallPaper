package com.example.wallsuper.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallsuper.R;

import java.io.File;
import java.util.List;

public class ImagePreviewAdapter extends PagerAdapter {
    private Activity activity;
    List<File> download;
    private String type;
    int pos;
    ImageView imageView;

    public ImagePreviewAdapter(Activity activity, List<File> download, String type, int pos) {
        this.activity = activity;
        this.download = download;
        this.type = type;
        this.pos = pos;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.image_preview, container, false);
        imageView = view.findViewById(R.id.imageView_image_show_adapter);

        if (type.matches("edited")) {
            Glide.with(activity).load(download.get(position).toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return download.size();
    }

    @Override
    public boolean isViewFromObject( View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
