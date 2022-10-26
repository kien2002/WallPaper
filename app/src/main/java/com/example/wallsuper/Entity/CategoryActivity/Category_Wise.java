package com.example.wallsuper.Entity.CategoryActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallsuper.Adapter.Custom_adp_home;
import com.example.wallsuper.Entity.WallPaper_Entities;
import com.example.wallsuper.FullScreenWallper;
import com.example.wallsuper.R;
import com.example.wallsuper.interfaces.AdapterCallbackWallpaper;
import com.example.wallsuper.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category_Wise extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterCallbackWallpaper {
    String name;
    Integer cid;

    RecyclerView recycleViewCategory;
    ArrayList<WallPaper_Entities> arrayList = new ArrayList<WallPaper_Entities>();
    SwipeRefreshLayout swpieCategory;
    Toolbar toolBarcategory;
    Custom_adp_home homeAdapter;
    WallPaper_Entities wallPaper_entities;

    private String MyAPI = "30427575-361fec803141b69aa7e4da935";
    String URL = "https://pixabay.com/api/?key=" + MyAPI;

    private boolean isScrolling = false;
    private int currentItem, totalView, scrollItem;
    int pageNumber = 2, perPage = 200;

    private AdView mAdView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise);
        wallPaper_entities = new WallPaper_Entities(this);
        Desclaration();
        homeAdapter = new Custom_adp_home(arrayList, getApplication(), Category_Wise.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplication(), 3, GridLayoutManager.VERTICAL, false);
        recycleViewCategory.setLayoutManager(gridLayoutManager);
        recycleViewCategory.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
        Inital();

        MobileAds.initialize(getApplication(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewWise);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void Desclaration() {
        recycleViewCategory = findViewById(R.id.recycleViewCategory);
        swpieCategory = findViewById(R.id.swpieCategory);
        toolBarcategory = findViewById(R.id.toolBarcategory);
    }

    private void Inital() {
        getIntentData();
        setUpToolBar();
        setSwipeFresh();
        callApiEditor();
        Utils.setStatusBlack(Category_Wise.this);
    }

    @SuppressLint("ResourceAsColor")
    private void setSwipeFresh() {
        swpieCategory.setOnRefreshListener(this);
        swpieCategory.setRefreshing(true);
        swpieCategory.setColorSchemeColors(R.color.yellow);
        swpieCategory.setProgressBackgroundColorSchemeColor(R.color.black);
    }

    private void setUpToolBar() {
        setSupportActionBar(toolBarcategory);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarcategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("cname");
        cid = intent.getIntExtra("cid", 0);
    }

    @Override
    public void onRefresh() {
        swpieCategory.setRefreshing(true);
        callApiEditor();
    }

    private void callApiEditor() {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "&category=" + name+ "&page=" + pageNumber + "&per_page=" + perPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hits");
                            int length = jsonArray.length();

                            for (int i = 0; i < length; i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String id = jsonObject1.getString("id");

                                String image = jsonObject1.getString("largeImageURL");

                                arrayList.add(new WallPaper_Entities(id, image));
                            }
                            homeAdapter.notifyDataSetChanged();
                            pageNumber++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (wallPaper_entities.status == true) {
                            swpieCategory.setRefreshing(false);
                            callApiEditor();
                        } else {
                            swpieCategory.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error", String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("key", MyAPI);
                return map;
            }
        };
        Context context = getApplication();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    public void onWallpaperClicked(String cposition, Integer wid, String linkImg, ArrayList<WallPaper_Entities> arrayList) {
        Intent intent = new Intent(Category_Wise.this, FullScreenWallper.class);
        intent.putExtra("cposition", cposition);
        intent.putExtra("wid", wid);
        intent.putExtra("linkImg", linkImg);
        intent.putExtra("arrayList", arrayList);
        startActivity(intent);
    }
}