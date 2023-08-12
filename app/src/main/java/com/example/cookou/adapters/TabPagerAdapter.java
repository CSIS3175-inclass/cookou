package com.example.cookou.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cookou.FragmentRecipeDetails;
import com.example.cookou.FragmentRecipeIngredients;
import com.example.cookou.FragmentRecipeInstructions;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.tabCount= behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentRecipeDetails();
            case 1:
                return new FragmentRecipeIngredients();
            case 2:
                return new FragmentRecipeInstructions();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
