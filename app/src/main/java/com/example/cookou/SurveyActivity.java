package com.example.cookou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cookou.adapters.ItemsRecyclerAdapter;
import com.example.cookou.database.DatabaseHelper;
import com.example.cookou.databinding.ActivitySurveyBinding;
import com.example.cookou.models.AreaModel;
import com.example.cookou.models.CategoryModel;
import com.example.cookou.models.User;
import com.example.cookou.services.RecipesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurveyActivity extends AppCompatActivity {
    ActivitySurveyBinding binding;
    RecyclerView categoriesRecyclerView;
    RecyclerView areasRecyclerView;
    Button startButton;
    ItemsRecyclerAdapter categoryAdapter;
    ItemsRecyclerAdapter areaAdapter;
    ArrayList<String> categoryList;
    ArrayList<String> categorySelections;
    ArrayList<String> areaList;
    ArrayList<String> areaSelections;
    Retrofit retrofit;
    RecipesService recipesService;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoriesRecyclerView=binding.recyclerviewCategories;
        areasRecyclerView=binding.recyclerviewAreas;
        startButton=binding.buttonStart;
        databaseHelper = new DatabaseHelper(this);

        categoryList=new ArrayList<>();
        categorySelections=new ArrayList<>();
        areaList=new ArrayList<>();
        areaSelections=new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipesService = retrofit.create(RecipesService.class);
        
        loadData();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadRecipes();
            }
        });

    }

    private void LoadRecipes() {
        SharedPreferences sharedPreferences = getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail","");
        User user = databaseHelper.getUser(email);
        user.updatePreferences(SurveyActivity.this,categorySelections,areaSelections, databaseHelper);

        Intent intent = new Intent(SurveyActivity.this,RecipeMenuActivity.class);
        startActivity(intent);
    }

    private void loadData() {
//        Create Thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                populateCategories();
                populateAreas();
                return ("Task completed");
            }
        });
    }

    private void populateAreas() {
        recipesService.listAreas().enqueue(new Callback<AreaModel>() {
            @Override
            public void onResponse(Call<AreaModel> call, Response<AreaModel> response) {
                for(AreaModel.Area area:response.body().getMeals()){
                    areaList.add(area.getStrArea());
                }
                areaAdapter = new ItemsRecyclerAdapter(areaList,areaSelections,SurveyActivity.this);
                areasRecyclerView.setLayoutManager(new GridLayoutManager(SurveyActivity.this,3));
                areasRecyclerView.setAdapter(areaAdapter);
            }

            @Override
            public void onFailure(Call<AreaModel> call, Throwable t) {
                Log.e(getClass().toString(),"Couldn't fetch areas."+t.toString());
            }
        });
    }

    private void populateCategories() {
        recipesService.listCategories().enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                for (CategoryModel.Category category:response.body().getMeals()) {
                    categoryList.add(category.getStrCategory());
                }
                categoryAdapter = new ItemsRecyclerAdapter(categoryList,categorySelections,SurveyActivity.this);
                categoriesRecyclerView.setLayoutManager(new GridLayoutManager(SurveyActivity.this,3));
                categoriesRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.e(getClass().toString(),"Couldn't fetch categories."+t.toString());
            }
        });

    }

}