package com.example.wallsuper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wallsuper.Adapter.ImagePreviewAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EditImagePreview extends AppCompatActivity {
    ImageView imageView, img_share, img_down, img_delete;
    ViewPager viewPager;
    String data;
    public static List<File> download;
    AlertDialog.Builder builder;
    ImagePreviewAdapter showAdapter;
    String arrayType, root;
    File file;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image_preview);
        Declation();
        Inital();
        MobileAds.initialize(EditImagePreview.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewEdit);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void Inital() {
        Intent intent = getIntent();
        data = intent.getStringExtra("path");

        int pos = intent.getIntExtra("pos", 0);
        arrayType = intent.getStringExtra("type");

        if (!data.matches("1")) {
            Glide.with(this).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            download = new ArrayList<>();
            download.clear();
            download.add(new File(data));
        } else {
            if (arrayType.equals("download")) {
                root = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/WALLSUPER/My Download/"));
            } else if (arrayType.equals("edited")) {
                root = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/WALLSUPER/EDITED WALLPAPER/"));
            }
            file = new File(root);
            download = new ArrayList<>();
            download.clear();
            download = getListFile(file);

            if (download.size() == 0) {

            } else {
                showAdapter = new ImagePreviewAdapter(EditImagePreview.this, download, "edited", pos);
                viewPager.setAdapter(showAdapter);
                viewPager.addOnPageChangeListener(viewPagerPageChange);
                setCurrentItem(pos);
            }
        }
        HandleClick();
    }


    private void setCurrentItem(int pos) {
        viewPager.setCurrentItem(pos, false);
    }

    ViewPager.OnPageChangeListener viewPagerPageChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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


    private void HandleClick() {
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeFile(download.get(viewPager.getCurrentItem()).toString());
                shareImage(bitmap);
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage();
            }
        });
        img_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    downloadImage();
                }
            }
        });
    }

    private void downloadImage() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        Uri uri;
        if (!data.matches("1")) {
            File imageFile = new File(data);
            uri = Uri.fromFile(imageFile);
        } else {
            File imageFile = new File(download.get(viewPager.getCurrentItem()).toString());
            uri = Uri.fromFile(imageFile);
        }
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra("mimeType", "image/jpeg");
        startActivity(Intent.createChooser(intent, "Set Wallpaper As"));
    }

    private void deleteImage() {
        builder = new AlertDialog.Builder(this);
        try {
            builder.setMessage("Do you want to delete it ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    File file = new File(download.get(viewPager.getCurrentItem()).toString());
                    file.delete();
                    download.remove(viewPager.getCurrentItem());
                    if (download.size() == 0) {
                        finish();
                    } else if (download.size() > 0) {
                        showAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(getApplicationContext(), "Wallpaper Delete Successfully", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Alert");
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareImage(Bitmap bitmap) {
        try {
            File file = new File(getExternalCacheDir(), File.separator + "logo.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(EditImagePreview.this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");
            EditImagePreview.this.startActivity(Intent.createChooser(intent, "Share Image"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(EditImagePreview.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(EditImagePreview.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Declation() {
        imageView = findViewById(R.id.iv_img);
        viewPager = findViewById(R.id.viewpager_image);
        img_share = findViewById(R.id.img_share);
        img_down = findViewById(R.id.img_down);
        img_delete = findViewById(R.id.img_delete);
    }

}