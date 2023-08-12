package com.example.cookou.services;

import com.example.cookou.models.AreaModel;
import com.example.cookou.models.CategoryModel;
import com.example.cookou.models.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipesService {
    @GET("list.php?c=list")
    Call<CategoryModel> listCategories();

    @GET("list.php?a=list")
    Call<AreaModel> listAreas();

    @GET("filter.php")
    Call<RecipeModel> listRecipeByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<RecipeModel> listRecipeByArea(@Query("a") String area);


    @GET("search.php?s=")
    Call<RecipeModel> listRecipes();

    @GET("lookup.php")
    Call<RecipeModel> getRecipe(@Query("i") String id);
}
