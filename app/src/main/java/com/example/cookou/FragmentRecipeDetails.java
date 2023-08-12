package com.example.cookou;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FragmentRecipeDetails extends Fragment {
    TextView name, source, description, tags;
    ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_recipe_details,container,false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
        String recipeName = sharedPreferences.getString("recipeName","");
        String recipeSource = sharedPreferences.getString("recipeSource","");
        String recipeDescription = sharedPreferences.getString("recipeDescription","");
        String recipeTags = sharedPreferences.getString("recipeTags","");
        String recipeImg = sharedPreferences.getString("recipeImg","");

        name = view.findViewById(R.id.textview_recipe_name);
        source = view.findViewById(R.id.textview_recipe_source);
        description = view.findViewById(R.id.textview_recipe_description_details);
        tags = view.findViewById(R.id.textview_recipe_category_list);
        image =view.findViewById(R.id.image_recipe_details);

        Glide.with(getContext()).load(recipeImg).into(image);
        name.setText(recipeName.toString());
        source.setText(recipeSource.toString());
        description.setText(recipeDescription.toString());
        tags.setText(recipeTags.toString());

        return view;
    }
}
