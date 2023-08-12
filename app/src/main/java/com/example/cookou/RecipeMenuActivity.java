package com.example.cookou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.cookou.adapters.ItemsRecyclerAdapter;
import com.example.cookou.adapters.RecipesRecyclerAdapter;
import com.example.cookou.database.DatabaseHelper;
import com.example.cookou.databinding.ActivityRecipeMenuBinding;
import com.example.cookou.databinding.ActivitySurveyBinding;
import com.example.cookou.models.AreaModel;
import com.example.cookou.models.RecipeModel;
import com.example.cookou.services.RecipesService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeMenuActivity extends AppCompatActivity {
    RecyclerView recipeRecyclerView;
    ActivityRecipeMenuBinding binding;
    DatabaseHelper databaseHelper;
    Set<RecipeModel.Meal> mealFinalSet;
    Retrofit retrofit;
    RecipesService recipesService;
    RecipesRecyclerAdapter adapter;
    Future<String> future;
    ArrayList<RecipeModel.Meal> mealArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecipeMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recipeRecyclerView=binding.recyclerviewRecipes;
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        mealFinalSet = new HashSet<>();
        mealArrayList = new ArrayList<>();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipesService = retrofit.create(RecipesService.class);

        loadData();

    }

    private void loadData() {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        future = executorService.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
                Set<String> categories = new HashSet<>();
                Set<String> areas = new HashSet<>();
                SharedPreferences sharedPreferences = getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
                categories = sharedPreferences.getStringSet("categoryList", new HashSet<>());
                areas = sharedPreferences.getStringSet("areaList", new HashSet<>());

                populateRecipes(categories, areas);
//                return ("Task completed");
//            }
//        });

    }

    private void populateRecipes(Set<String> categories,Set<String> areas) {
        adapter = new RecipesRecyclerAdapter(mealArrayList,RecipeMenuActivity.this);
        recipeRecyclerView.setAdapter(adapter);
        if(categories.isEmpty()&&areas.isEmpty()){
            populateByDefault();
        } else if (categories.isEmpty()) {
            populateByCategory(categories);
        } else if (areas.isEmpty()) {
            populateByArea(areas);
        }
        else{
            populateByCategory(categories);
            populateByArea(areas);
        }

    }

    private void populateByDefault() {
        recipesService.listRecipes().enqueue(new Callback<RecipeModel>() {
            @Override
            public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response) {
                for(RecipeModel.Meal meal:response.body().getMeals()){
                    mealFinalSet.add(meal);
                }
                for(RecipeModel.Meal meal : mealFinalSet){
                    mealArrayList.add(meal);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RecipeModel> call, Throwable t) {
                Log.e(getClass().toString(),"Couldn't fetch recipes."+t.toString());
            }
        });

//        try {
//            Response<RecipeModel> response = recipesService.listRecipes().execute();
//            for(RecipeModel.Meal meal:response.body().getMeals()){
//                mealFinalSet.add(meal);
//            }
//
//            for(RecipeModel.Meal meal : mealFinalSet){
//                mealArrayList.add(meal);
//            }
//
//            adapter = new RecipesRecyclerAdapter(mealArrayList,RecipeMenuActivity.this);
//            recipeRecyclerView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void populateByArea(Set<String> areas) {
        for(String area: areas){
            recipesService.listRecipeByArea(area).enqueue(new Callback<RecipeModel>() {
                @Override
                public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response) {
                    for(RecipeModel.Meal meal:response.body().getMeals()){
                        mealFinalSet.add(meal);
                    }
                    for(RecipeModel.Meal meal : mealFinalSet){
                        mealArrayList.add(meal);
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<RecipeModel> call, Throwable t) {
                    Log.e(getClass().toString(),"Couldn't fetch recipes."+t.toString());
                }
            });
        }
    }

    private void populateByCategory(Set<String> categories) {
        for(String category: categories){
            recipesService.listRecipeByCategory(category).enqueue(new Callback<RecipeModel>() {
                @Override
                public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response) {
                    for(RecipeModel.Meal meal:response.body().getMeals()){
                        mealFinalSet.add(meal);
                        Log.e("NO ERROR","Done!//////////// "+ meal.getStrInstructions());
                    }
                    for(RecipeModel.Meal meal : mealFinalSet){
                        mealArrayList.add(meal);

                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<RecipeModel> call, Throwable t) {
                    Log.e(getClass().toString(),"Couldn't fetch recipes."+t.toString());

                }
            });
        }
    }

}