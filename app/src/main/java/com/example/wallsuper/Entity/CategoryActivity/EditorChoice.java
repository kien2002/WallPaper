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

public class EditorChoice extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterCallbackWallpaper {
    String cname;
    Integer cid;
    RecyclerView recyclerViewCate;
    ArrayList<WallPaper_Entities> arrayList = new ArrayList<WallPaper_Entities>();
    SwipeRefreshLayout swipeCate;
    Toolbar toolbar;
    Custom_adp_home homeAdapter;
    WallPaper_Entities wallPaper_entities;

    private String MyAPI = "30427575-361fec803141b69aa7e4da935";
    String URL = "https://pixabay.com/api/?key=" + MyAPI;

    private boolean isScrolling = false;
    private int currentItem, totalView, scrollItem;
    int pageNumber = 1, perPage = 200;

    private AdView mAdView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_choice);
        wallPaper_entities = new WallPaper_Entities(this);
        Declartion();
        Initial();
        homeAdapter = new Custom_adp_home(arrayList, getApplication(), EditorChoice.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplication(), 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCate.setLayoutManager(gridLayoutManager);
        recyclerViewCate.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
        MobileAds.initialize(getApplication(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewChoice);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void Initial() {
        getIntentData();
        setUpToolBar();
        setSwipeFresh();
        callApiEditor();
        Utils.setStatusBlack(EditorChoice.this);
    }

    @SuppressLint("ResourceAsColor")
    private void setSwipeFresh() {
        swipeCate.setOnRefreshListener(this);
        swipeCate.setRefreshing(true);
        swipeCate.setColorSchemeColors(R.color.yellow);
        swipeCate.setProgressBackgroundColorSchemeColor(R.color.black);
    }

    private void callApiEditor() {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "&editors_choice=" + "&page=" + pageNumber + "&per_page=" + perPage,
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
                            swipeCate.setRefreshing(false);
                            callApiEditor();
                        } else {
                            swipeCate.setRefreshing(false);
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

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        cname = intent.getStringExtra("cname");
    }

    private void Declartion() {
        recyclerViewCate = findViewById(R.id.recycleViewCate);
        swipeCate = findViewById(R.id.swpieCate);
        toolbar = findViewById(R.id.toolBarcate);
    }

    @Override
    public void onRefresh() {
        swipeCate.setRefreshing(true);
        callApiEditor();
    }

    public void onWallpaperClicked(String cposition, Integer wid, String linkImg, ArrayList<WallPaper_Entities> arrayList) {
        Intent intent = new Intent(EditorChoice.this, FullScreenWallper.class);
        intent.putExtra("cposition", cposition);
        intent.putExtra("wid", wid);
        intent.putExtra("linkImg", linkImg);
        intent.putExtra("arrayList", arrayList);
        startActivity(intent);
    }
}