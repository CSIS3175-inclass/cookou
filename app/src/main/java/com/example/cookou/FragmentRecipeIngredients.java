package com.example.cookou;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class FragmentRecipeIngredients extends Fragment {
    TextView Ingredient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_recipe_ingredient,container,false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
//        String recipeMeasures = sharedPreferences.getString("strMeasures","");
        Set<String> recipeIngredients = sharedPreferences.getStringSet("strIngredients",new HashSet<>());
        String result="";
        for (String i : recipeIngredients){
            result+=i+"\n";
        }

        Ingredient = view.findViewById(R.id.textview_ingredient_name);
        Ingredient.setText(result);

        return view;
    }
}
