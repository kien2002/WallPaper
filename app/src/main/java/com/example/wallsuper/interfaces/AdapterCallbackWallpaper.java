package com.example.wallsuper.interfaces;

import com.example.wallsuper.Entity.WallPaper_Entities;

import java.util.ArrayList;

public interface AdapterCallbackWallpaper {

    public default void onWallpaperClicked(String cposition,Integer wid, String linkImg, ArrayList<WallPaper_Entities> arrayList) {

    }
}
