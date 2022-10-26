package com.example.wallsuper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.wallsuper.Adapter.PageAdapter;
import com.example.wallsuper.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabCategory, tabHome, tabPremium, tabPopular, tabMonth, tabTrending;
    private PageAdapter pageAdapter;
    String fragmentPosition = "1";

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBarMain);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPage);

        tabCategory = findViewById(R.id.tabCategory);
        tabHome = findViewById(R.id.tabHome);
        tabPremium = findViewById(R.id.tabPremium);
        tabPopular = findViewById(R.id.tabPopular);
        tabMonth = findViewById(R.id.tabMonthlyPopular);
        tabTrending = findViewById(R.id.tabTrending);


        setUpDrawerLayout();
        Utils.setStatusBlack(MainActivity.this);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0) {
                    fragmentPosition = "0";
                } else if (tab.getPosition() == 1) {
                    fragmentPosition = "1";
                } else if (tab.getPosition() == 2) {
                    fragmentPosition = "2";
                } else if (tab.getPosition() == 3) {
                    fragmentPosition = "3";
                } else if (tab.getPosition() == 4) {
                    fragmentPosition = "4";
                } else if (tab.getPosition() == 5) {
                    fragmentPosition = "5";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setUpDrawerLayout() {

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_dowload) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (MainActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(MainActivity.this, permission, WRITE_EXTERNAL_STORAGE_CODE);
                } else {
                    Intent intent = new Intent(this, MyDownload.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(this, MyDownload.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_edited) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (MainActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(MainActivity.this, permission, WRITE_EXTERNAL_STORAGE_CODE);
                } else {
                    Intent intent = new Intent(this, MyEdit.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(this, MyEdit.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_share) {
            ShareApplication();
        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private void ShareApplication() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        final String appPackageName = this.getPackageName();
        String strAppLink = "";
        try {
            strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
        } catch (Exception e) {
            strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
        }
        intent.setType("text/link");
        String shareBody = "Download latest collection of all type 4K for free from SuperWall" + "\n" + "" + strAppLink;
        String shareSub = "APP NAME/TITLE";
        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Share Link Using "));
    }

    private void openPlayStore(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}