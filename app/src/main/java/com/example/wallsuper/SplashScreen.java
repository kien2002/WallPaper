package com.example.wallsuper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallsuper.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class SplashScreen extends AppCompatActivity {

    TextView txtName;
    private static int Splash_Screen_TimeOut = 10000;
    private InterstitialAd mInterstitialAd;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();

        if (!Utils.isConnected(SplashScreen.this)) showDialog(SplashScreen.this).show();

        else {
            try {
                txtName = findViewById(R.id.txtName);
                setContentView(R.layout.activity_splash_screen);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        if (mInterstitialAd.isLoaded())
                            mInterstitialAd.show();
                    }
                }, 8000);

                MobileAds.initialize(this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {

                    }
                });
                mInterstitialAd = new InterstitialAd(this);
                mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

                Animation ani1 = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.anim);
                txtName.startAnimation(ani1);
            } catch (Exception e) {
                String a = "";
            }

        }
    }

    public AlertDialog.Builder showDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.check_internet_dialog, null);
        Button btnRetry = view.findViewById(R.id.btnRetry);
        builder.setView(view);

        Dialog dialog = new Dialog(SplashScreen.this);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return builder;
    }
}