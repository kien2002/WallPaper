package com.example.wallsuper.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
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
import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.example.wallsuper.Adapter.Custom_adp_home;
import com.example.wallsuper.Entity.WallPaper_Entities;
import com.example.wallsuper.FullScreenWallper;
import com.example.wallsuper.R;
import com.example.wallsuper.interfaces.AdapterCallbackWallpaper;
import com.example.wallsuper.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopularFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterCallbackWallpaper {

    RecyclerView recyclerView;
    WallPaper_Entities wallPaper_entities;
    ArrayList<WallPaper_Entities> arrayList = new ArrayList<WallPaper_Entities>();
    SwipeRefreshLayout swipeRefreshLayout;
    View view;
    RecyclerView.LayoutManager layoutManager;
    Custom_adp_home homeAdapter;
    boolean isShowing_progress;
    Activity activity;

    private String MyAPI = "30427575-361fec803141b69aa7e4da935";
    String URL = "https://pixabay.com/api/?key=" + MyAPI;

    private boolean isScrolling = false;
    private int currentItem, totalView, scrollItem;
    int pageNumber = 3, perPage = 200;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static AdmobNativeAdAdapter admobNativeAdAdapter;
    private Activity context;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mContext = getContext();
        this.context = (Activity) context;
        if (!Utils.isConnected(getContext())) showDialog(getContext()).show();
        else {
            wallPaper_entities = new WallPaper_Entities(this);
            view = inflater.inflate(R.layout.fragment_popular, container, false);
            Declartion();
            recyclerView = view.findViewById(R.id.rcy_viewPopular);
//ads
            MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mAdView = view.findViewById(R.id.adViewPopu);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            //data
            Initial();
            homeAdapter = new Custom_adp_home(arrayList, getContext(), PopularFragment.this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(homeAdapter);
            homeAdapter.notifyDataSetChanged();
        }
        return view;
    }

    public AlertDialog.Builder showDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.check_internet_dialog, null);
        Button btnRetry = view.findViewById(R.id.btnRetry);
        builder.setView(view);

        Dialog dialog = new Dialog(getContext());

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
        return builder;
    }

    @SuppressLint("ResourceAsColor")
    private void Declartion() {
        recyclerView = view.findViewById(R.id.rcy_viewPopular);
        swipeRefreshLayout = view.findViewById(R.id.swipeViewPopular);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(R.color.yellow);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(R.color.black);
    }

    private void Initial() {
        callAPI();
    }

    private void callAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "&page=" + pageNumber + "&per_page=" + perPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("hits");
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        String id = json.getString("id");

                        String image = json.getString("largeImageURL");
                        arrayList.add(new WallPaper_Entities(id, image));
                    }
                    homeAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", String.valueOf(e));
                }

                if (wallPaper_entities.status == true) {
                    swipeRefreshLayout.setRefreshing(false);
                    callAPI();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", MyAPI);
                return map;
            }
        };
        Context context = getActivity();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        callAPI();
    }

    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onWallpaperClicked(String cposition, Integer wid, String linkImg, ArrayList<WallPaper_Entities> arrayList) {

        Intent intent = new Intent(getContext(), FullScreenWallper.class);
        intent.putExtra("cposition", cposition);
        intent.putExtra("wid", wid);
        intent.putExtra("linkImg", linkImg);
        intent.putExtra("arrayList", arrayList);
        startActivity(intent);
    }
}