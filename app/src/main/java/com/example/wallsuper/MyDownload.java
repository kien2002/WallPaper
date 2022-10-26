package com.example.wallsuper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wallsuper.Adapter.ImageAdapter;
import com.example.wallsuper.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyDownload extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView textMydownload;
    SwipeRefreshLayout swipeMydownload;
    RecyclerView recycleView_Mydownload;
    Toolbar toolbar;
    String root;
    File file;
    List<File> download;
    ImageAdapter imageAdapter;

    private AdView mAdView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);
        Decleration();
        Initial();

        MobileAds.initialize(MyDownload.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewDL);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void Decleration() {
        textMydownload = findViewById(R.id.textMydownload);
        swipeMydownload = findViewById(R.id.swipeMydownload);
        recycleView_Mydownload = findViewById(R.id.recycleView_Mydownload);
        toolbar = findViewById(R.id.toolBarMD);
    }

    private void Initial() {

        setUpToolBar();
        setSwipeRefresh();
        getImageInFolder();
        Utils.setStatusBlack(MyDownload.this);

    }

    private void getImageInFolder() {
        root = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/WALLSUPER/My Download/"));
        file = new File(root);
        if (!file.exists()) {
            file.mkdir();
        }
        download = new ArrayList<>();
        download.clear();
        download = getListFile(file);

        if (download.isEmpty()) {
            textMydownload.setVisibility(View.VISIBLE);
            swipeMydownload.setVisibility(View.GONE);
            swipeMydownload.setRefreshing(false);

        } else {
            textMydownload.setVisibility(View.GONE);
            recycleView_Mydownload.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MyDownload.this, 3);
            recycleView_Mydownload.setLayoutManager(layoutManager);
            imageAdapter = new ImageAdapter(MyDownload.this, download, "download");
            recycleView_Mydownload.setAdapter(imageAdapter);
            swipeMydownload.setRefreshing(false);
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
        swipeMydownload.setRefreshing(true);
        swipeMydownload.setOnRefreshListener(this);
        swipeMydownload.setColorSchemeColors(R.color.yellow);
        swipeMydownload.setProgressBackgroundColorSchemeColor(R.color.black);
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Download");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeMydownload.setRefreshing(false);
    }

}