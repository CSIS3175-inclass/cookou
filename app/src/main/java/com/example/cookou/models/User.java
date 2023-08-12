package com.example.cookou.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cookou.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String name, email, password;
    private boolean isActive;

    public User(String name, String email, String password, boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public User() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void updatePreferences(Context context, ArrayList<String > categoryList,ArrayList<String > areaList, DatabaseHelper databaseHelper) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> categorySet = new HashSet<String>();
        categorySet.addAll(categoryList);
        Set<String> areaSet = new HashSet<String>();
        areaSet.addAll(areaList);

        editor.putStringSet("categoryList", categorySet);
        editor.putStringSet("areaList", areaSet);
        editor.apply();

        for(String category: categoryList){
            databaseHelper.updateUserDiets(category,this.id);
        }

        for(String area: areaList){
            databaseHelper.updateUserAreas(area,this.id);
        }
    }
}
