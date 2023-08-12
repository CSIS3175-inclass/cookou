package com.example.cookou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.cookou.adapters.RecipesRecyclerAdapter;
import com.example.cookou.adapters.TabPagerAdapter;
import com.example.cookou.models.RecipeModel;
import com.example.cookou.services.RecipesService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDetailActivity extends AppCompatActivity {
    Retrofit retrofit;
    RecipesService recipesService;
    ArrayList<RecipeModel.Meal> mealArrayList;
    SharedPreferences sharedPreferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mealArrayList = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipesService = retrofit.create(RecipesService.class);

        sharedPreferences = getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
        id= sharedPreferences.getString("recipeId","");

        getRecipe(id);

    }

    private void getRecipe(String id) {
        recipesService.getRecipe(id).enqueue(new Callback<RecipeModel>() {
            @Override
            public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response) {
                for(RecipeModel.Meal meal:response.body().getMeals()){
                    mealArrayList.add(meal);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("recipeName",meal.getStrMeal());
                    editor.putString("recipeSource",meal.getStrSource());
                    editor.putString("recipeDescription",meal.getStrInstructions());
                    editor.putString("recipeTags",meal.getStrTags());
                    editor.putString("recipeImg",meal.getStrMealThumb());
                    editor.putStringSet("strIngredients", meal.getAllIngredients());
                    editor.putStringSet("strMeasures", meal.getAllMeasurements());
                    editor.putString("recipeInstructions", meal.getStrInstructions());

                    editor.apply();
                }

                configureTabLayout();


            }

            @Override
            public void onFailure(Call<RecipeModel> call, Throwable t) {
                Log.e(getClass().toString(),"Couldn't fetch recipes."+t.toString());
            }
        });
    }

    public void configureTabLayout(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_recipe));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_ingredients));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_instructions));


        final ViewPager viewPager = findViewById(R.id.view_pager);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}