package com.example.wallsuper;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.misc.AsyncTask;
import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.example.wallsuper.Adapter.FullScreenWallAdapter;
import com.example.wallsuper.Entity.WallPaper_Entities;
import com.example.wallsuper.utils.BottomSheetDialog;
import com.example.wallsuper.utils.FileUtils;
import com.example.wallsuper.utils.ProgessDialog;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import iamutkarshtiwari.github.io.ananas.editimage.EditImageActivity;
import iamutkarshtiwari.github.io.ananas.editimage.ImageEditorIntentBuilder;

public class FullScreenWallper extends AppCompatActivity {
    String cposition, linkImg;
    Integer wid;
    ArrayList<WallPaper_Entities> arrayList = new ArrayList<>();
    private ViewPager viewPagerslider;
    FullScreenWallAdapter fullScreenWallAdapter;
    ImageView img_share, img_down, img_edit;
    Bitmap bitmap;
    private static final int WRITE_EXTERNAL_STORAGE_CODES = 1;
    String urlType = "";

    String fileName;
    long downloadRefences;
    private final int PHOTO_EDITOR_REQUEST_CODE = 231;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static AdmobNativeAdAdapter admobNativeAdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallper);
        Declaration();
        Initial();
        MobileAds.initialize(FullScreenWallper.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewFULL);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    private void Initial() {
        getIntentData();
        setUpSlider();
        HandleClick();
    }

    private void HandleClick() {
        img_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
                urlType = "DOWNLOAD";
                ProgessDialog.showDialog(FullScreenWallper.this);
                String stringUrl = arrayList.get(viewPagerslider.getCurrentItem()).getLink();
                new GetImgFromUrl().execute(stringUrl);
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlType = "SHARE";
                String stringUrl = arrayList.get(viewPagerslider.getCurrentItem()).getLink();
                new GetImgFromUrl().execute(stringUrl);
            }
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    if (checkWrite()) {
                        ProgessDialog.showDialog(FullScreenWallper.this);
                        String url = arrayList.get(viewPagerslider.getCurrentItem()).getLink();
                        Integer wid = Integer.valueOf(arrayList.get(viewPagerslider.getCurrentItem()).getId());
                        downloadImageforEdit("test", url, "share", wid);
                    }
                }
            }
        });
    }

    private void downloadImageforEdit(String test, String url, String share, Integer wid) {
        try {
            fileName = String.valueOf(wid);
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadURI = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadURI);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).
                    setAllowedOverRoaming(false).setTitle(fileName).setMimeType("image/jpg").
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "WALLSUPER/My WallPaper/" + "SUPERWALL" + fileName + ".JPEG");
            downloadRefences = downloadManager.enqueue(request);
            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(downloadRecevierForEdit, filter);
        } catch (Exception e) {
            Toast.makeText(FullScreenWallper.this, "Image download failed", Toast.LENGTH_SHORT).show();
        }
    }

    private BroadcastReceiver downloadRecevierForEdit = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadRefences == referenceId) {
                String root = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/WALLSUPER/My WallPaper/" + "SUPERWALL" + fileName + ".JPEG"));
                ProgessDialog.HideProgress();
                if (checkPermissions()) {
                    if (checkWrite()) {
                        startEdit(root);
                    }
                }
            }
        }
    };

    void startEdit(String root) {
        File outFile = FileUtils.getEditFile();
        try {
            Intent intent = new ImageEditorIntentBuilder(this, root, outFile.getAbsolutePath())
                    .withAddText() // Add the features you need
                    .withPaintFeature()
                    .withFilterFeature()
                    .withRotateFeature()
                    .withCropFeature()
                    .withBrightnessFeature()
                    .withSaturationFeature()
                    .withBeautyFeature()
                    .withStickerFeature()
                    .forcePortrait(true)
                    .setSupportActionBarVisibility(false)
                    .build();
            EditImageActivity.start(FullScreenWallper.this, intent, PHOTO_EDITOR_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_EDITOR_REQUEST_CODE) {
            if (!(data == null)) {
                String newFilePath = data.getStringExtra(ImageEditorIntentBuilder.OUTPUT_PATH);
                boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IS_IMAGE_EDITED, false);
                if (isImageEdit) {
                    File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES
                            + "/WALLSUPER/My WallPaper/" + "SUPERWALL" + fileName + ".JPEG")));
                    file.delete();
                    startActivity(new Intent(FullScreenWallper.this, EditImagePreview.class).putExtra("path", newFilePath));
                } else {
                    newFilePath = data.getStringExtra(ImageEditorIntentBuilder.SOURCE_PATH);
                }
            }
        }
    }

    public boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean checkWrite() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
                return false;
            }
        } else {
            return true;
        }
    }

    public class GetImgFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ProgessDialog.HideProgress();
            if (urlType.equals("DOWNLOAD")) {
                openBottomDialog(bitmap);
            } else if (urlType.equals("SHARE")) {
                shareImage(bitmap);
            }

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
            Uri uri = FileProvider.getUriForFile(FullScreenWallper.this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");
            FullScreenWallper.this.startActivity(Intent.createChooser(intent, "Share Image"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(FullScreenWallper.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FullScreenWallper.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openBottomDialog(Bitmap bitmap) {
        String imgLink = arrayList.get(viewPagerslider.getCurrentItem()).getLink();
        Integer wid = Integer.valueOf(arrayList.get(viewPagerslider.getCurrentItem()).getId());
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FullScreenWallper.this, bitmap, imgLink, wid);
        bottomSheetDialog.show((FullScreenWallper.this).getSupportFragmentManager(), "examplebottomsheet");

    }

    private void Declaration() {
        viewPagerslider = findViewById(R.id.viewPageFull);
        img_share = findViewById(R.id.img_share);
        img_down = findViewById(R.id.img_down);
        img_edit = findViewById(R.id.img_edit);

    }

    private void setUpSlider() {
        fullScreenWallAdapter = new FullScreenWallAdapter(this, arrayList, linkImg, wid);
        viewPagerslider.setAdapter(fullScreenWallAdapter);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        cposition = intent.getStringExtra("cposition");
        linkImg = intent.getStringExtra("linkImg");
        arrayList = (ArrayList<WallPaper_Entities>) intent.getSerializableExtra("arrayList");
        wid = intent.getIntExtra("wid", 0);

    }

    public static void onWallpaperSet(Context context, Bitmap bitmap) {
        WallpaperManager manager = WallpaperManager.getInstance(context);
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(context, "Wallpaper Set Successfully !", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Wallpaper Is Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    public static void onWallpaperSetLock(Context context, Bitmap bitmap) {
        WallpaperManager manager = WallpaperManager.getInstance(context);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                Toast.makeText(context, "Wallpaper Set Successfully !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wallpaper Set Failed !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Wallpaper Is Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    public static void onWallpaperSetCustom(Context context, Bitmap bitmap) {
        try {
            File file = new File(context.getExternalCacheDir(), File.separator + "logo.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);

            Uri photoUri = FileProvider.getUriForFile(Objects.requireNonNull(context),
                    BuildConfig.APPLICATION_ID + ".provider", file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_ATTACH_DATA);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setDataAndType((photoUri), "image/jpg");
            intent.putExtra("mimeType", "image/jpg");
            context.startActivity(Intent.createChooser(intent, "Set Wallpaper As"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void onWallpaperDownload(Context context, String linkImg, Integer wid) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                ActivityCompat.requestPermissions((Activity) context, permissions, WRITE_EXTERNAL_STORAGE_CODES);
            } else {
                onWallpaperSaved(context, linkImg, wid);
            }
        } else {
            onWallpaperSaved(context, linkImg, wid);
        }
    }

    private static void onWallpaperSaved(Context context, String linkImg, Integer wid) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(linkImg));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("WALLSUPER");
        Toast.makeText(context, "DOWNLOAD IMAGE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "WALLSUPER/My Download/" + "SUPERWALL" + wid + ".JPEG");
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}