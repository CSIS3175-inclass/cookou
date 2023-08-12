package com.example.cookou.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookou.R;
import com.example.cookou.models.RecipeModel;

import java.util.ArrayList;

public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesRecyclerAdapter.ViewHolder>{
    private ArrayList<RecipeModel.Meal> recipeList;
    private Context context;

    public RecipesRecyclerAdapter(ArrayList<RecipeModel.Meal> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecipesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe, parent, false);
        RecipesRecyclerAdapter.ViewHolder viewHolder = new RecipesRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesRecyclerAdapter.ViewHolder holder, int position) {
        RecipeModel.Meal recipe = recipeList.get(position);
        holder.setImage(recipe.getStrMealThumb());
        holder.title.setText(recipe.getStrMeal());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, RecipeDetailActivity.class);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description, tags;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_recipe);
            title = itemView.findViewById(R.id.textview_recipe_name);
            cardView = itemView.findViewById(R.id.card_view);
        }
        public void setImage(String url){
            Glide.with(context).load(url).into(image);
        }
    }
}
