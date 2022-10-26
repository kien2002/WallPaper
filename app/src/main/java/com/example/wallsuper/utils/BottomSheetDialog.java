package com.example.wallsuper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wallsuper.Adapter.ItemWallAdapter;
import com.example.wallsuper.FullScreenWallper;
import com.example.wallsuper.Models.ItemW;
import com.example.wallsuper.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    Context context;
    Bitmap bitmap;
    String imgLink;
    Integer wid;

    public BottomSheetDialog(Context context, Bitmap bitmap, String imgLink, Integer wid) {
        this.context = context;
        this.bitmap = bitmap;
        this.imgLink = imgLink;
        this.wid = wid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        ArrayList<ItemW> arrayList = new ArrayList<>();
        arrayList.add(new ItemW(R.drawable.ic_baseline_wallpaper_24, "SET WALLPAPER"));
        arrayList.add(new ItemW(R.drawable.ic_baseline_lock_24, "SET LOCK SCREEN"));
        arrayList.add(new ItemW(R.drawable.ic_lock_both, "SET BOTH"));
        arrayList.add(new ItemW(R.drawable.ic_baseline_crop_24, "SET WALLPAPER AS CUSTOM"));
        arrayList.add(new ItemW(R.drawable.ic_baseline_arrow_circle_down_24, "DOWNLOAD WALLPAPER"));
        ItemWallAdapter adapter = new ItemWallAdapter(this.context, arrayList);
        final ListView listView = view.findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //set wallpaper
                        FullScreenWallper.onWallpaperSet(context, bitmap);
                        break;
                    case 1:
                        //set wallpaper lockscreen
                        FullScreenWallper.onWallpaperSetLock(context, bitmap);
                        break;
                    case 2:
                        //set wallpaper both
                        FullScreenWallper.onWallpaperSet(context, bitmap);
                        FullScreenWallper.onWallpaperSetLock(context, bitmap);
                        break;
                    case 3:
                        //set custom wall
                        FullScreenWallper.onWallpaperSetCustom(context, bitmap);
                        break;
                    case 4:
                        //download wallpaper
                        FullScreenWallper.onWallpaperDownload(context, imgLink, wid);
                        break;
                }
            }
        });
        return view;
    }
}
