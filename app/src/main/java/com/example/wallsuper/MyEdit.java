package com.example.wallsuper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.example.wallsuper.Adapter.ImageAdapter;
import com.example.wallsuper.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyEdit extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    TextView textMyEdit;
    SwipeRefreshLayout swipeMyEdit;
    RecyclerView recycleView_MyEdit;
    Toolbar toolbar;
    String root;
    File file;
    List<File> download;
    ImageAdapter imageAdapter;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static AdmobNativeAdAdapter admobNativeAdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit);
        Decleration();
        Initial();
        MobileAds.initialize(getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewEdit);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void Initial() {
        setUpToolBar();
        setSwipeRefresh();
        getImageInFolder();
        Utils.setStatusBlack(MyEdit.this);
    }

    private void getImageInFolder() {
        root = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/WALLSUPER/EDITED WALLPAPER/"));
        file = new File(root);
        if (!file.exists()) {
            file.mkdir();
        }
        download = new ArrayList<>();
        download.clear();
        download = getListFile(file);

        if (download.isEmpty()) {
            textMyEdit.setVisibility(View.VISIBLE);
            swipeMyEdit.setVisibility(View.GONE);
            swipeMyEdit.setRefreshing(false);

        } else {
            textMyEdit.setVisibility(View.GONE);
            recycleView_MyEdit.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MyEdit.this, 3);
            recycleView_MyEdit.setLayoutManager(layoutManager);
            imageAdapter = new ImageAdapter(MyEdit.this, download, "edited");
            recycleView_MyEdit.setAdapter(imageAdapter);
            swipeMyEdit.setRefreshing(false);
        }
    }

    private List<File> getListFile(File parent) {
        List<File> inFiles = new ArrayList<>();
        try {
            Queue<File> files = new LinkedList<>(Arrays.asList(parent.listFiles()));
            while (!files.isEmpty()) {
                File file = files.remove();
                if (file.isDirectory()) {
                    files.addAll(Arrays.asList(file.listFiles()));
                } else if (file.getName().endsWith(".JPEG")) {
                    inFiles.add(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inFiles;
    }

    @SuppressLint("ResourceAsColor")
    private void setSwipeRefresh() {
        swipeMyEdit.setRefreshing(true);
        swipeMyEdit.setOnRefreshListener(this);
        swipeMyEdit.setColorSchemeColors(R.color.yellow);
        swipeMyEdit.setProgressBackgroundColorSchemeColor(R.color.black);
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Edited Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Decleration() {
        textMyEdit = findViewById(R.id.textMyEdit);
        swipeMyEdit = findViewById(R.id.swipeMyEdit);
        recycleView_MyEdit = findViewById(R.id.recycleView_MyEdit);
        toolbar = findViewById(R.id.toolBarME);
    }

    @Override
    public void onRefresh() {
        swipeMyEdit.setRefreshing(false);
    }
}