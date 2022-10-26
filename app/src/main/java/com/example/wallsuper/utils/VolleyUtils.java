package com.example.wallsuper.utils;

import android.app.Activity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallsuper.interfaces.VolleyListener;

import org.json.JSONObject;

public class VolleyUtils {
    public static void makeRequest(final Activity activity, String url, final JSONObject jsonObject,
                                   int maxretry, boolean isShowing_progress, final VolleyListener volleyListener) {
        try {
            if (isShowing_progress) {
                Utils.showProgress(activity);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Utils.hideProgressBar();
                        volleyListener.onResponse(response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Utils.hideProgressBar();
                        Log.e("Error", String.valueOf(e));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Utils.hideProgressBar();
                    Log.e("Error", String.valueOf(error.getMessage()));
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, maxretry, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(activity).add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
