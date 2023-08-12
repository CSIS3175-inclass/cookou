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

import java.util.ArrayList;

public class FragmentRecipeInstructions extends Fragment {
    TextView instructionTxt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_recipe_instruction,container,false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
        String recipeInstructions = sharedPreferences.getString("recipeInstructions","");

//        String[] instuctionsList=recipeInstructions.split("\\.");

        instructionTxt = view.findViewById(R.id.textview_instruction);
        instructionTxt.setText(recipeInstructions);

        return view;
    }
}
