package com.example.wallsuper.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wallsuper.fragment.CategoryFragment;
import com.example.wallsuper.fragment.HomeFragment;
import com.example.wallsuper.fragment.MonthlyPopular;
import com.example.wallsuper.fragment.PopularFragment;
import com.example.wallsuper.fragment.PremiumFragment;
import com.example.wallsuper.fragment.TrendingFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTab;

    public PageAdapter(@NonNull FragmentManager fm, int numOfTab) {
        super(fm);
        this.numOfTab = numOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CategoryFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new PremiumFragment();
            case 3:
                return new PopularFragment();
            case 4:
                return new MonthlyPopular();
            case 5:
                return new TrendingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
