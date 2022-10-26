package com.example.wallsuper.Entity;


import com.example.wallsuper.Entity.CategoryActivity.Category_Wise;
import com.example.wallsuper.Entity.CategoryActivity.EditorChoice;
import com.example.wallsuper.fragment.HomeFragment;
import com.example.wallsuper.fragment.MonthlyPopular;
import com.example.wallsuper.fragment.PopularFragment;
import com.example.wallsuper.fragment.PremiumFragment;
import com.example.wallsuper.fragment.TrendingFragment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WallPaper_Entities implements Serializable {
    @SerializedName("status")
    @Expose
    public boolean status;

    String id;
    String link;

    public WallPaper_Entities(HomeFragment homeFragment) {

    }

    public WallPaper_Entities(String id, String link) {
        this.id = id;
        this.link = link;
    }

    public WallPaper_Entities(EditorChoice editorChoice) {
    }

    public WallPaper_Entities(Category_Wise category_wise) {
    }

    public WallPaper_Entities(PremiumFragment premiumFragment) {
    }

    public WallPaper_Entities(PopularFragment popularFragment) {
    }

    public WallPaper_Entities(MonthlyPopular monthlyPopular) {

    }

    public WallPaper_Entities(TrendingFragment trendingFragment) {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
