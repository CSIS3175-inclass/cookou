package com.example.cookou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cookou.database.DatabaseHelper;
import com.example.cookou.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
                            implements MainListener{
    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper=new DatabaseHelper(this);
        fragmentManager = getSupportFragmentManager();

        switchTo("fragIdentification");

    }

    @Override
    public void switchTo(String tag) {
        switch (tag){
            case "fragLogin":{
                FragmentLogin fragmentLogin = new FragmentLogin();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragmentLogin, "fragIdentification");
                transaction.commit();
                break;
            }
            case "fragRegister":{
                FragmentRegister fragmentRegister = new FragmentRegister();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragmentRegister, "fragIdentification");
                transaction.commit();
                break;
            }
            case "fragIdentification":{
                FragmentIdentification FragmentIdentification = new FragmentIdentification();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, FragmentIdentification, "fragIdentification");
                transaction.commit();
                break;
            }
            default: {
                Log.e(MainActivity.class.getSimpleName(),tag+" is not a valid fragment tag");
            }
        }

    }

    @Override
    public DatabaseHelper getDatabasehelper() {
        return databaseHelper;

    }

    @Override
    public void goTo(String activity) {
        switch (activity){
            case "SurveyActivity":{
                Intent intent = new Intent(MainActivity.this,SurveyActivity.class);
                startActivity(intent);

                break;
            }
            case "RecipeMenuActivity":{
                Intent intent = new Intent(MainActivity.this,RecipeMenuActivity.class);
                startActivity(intent);
                break;
            }
            default: {
                Log.e(MainActivity.class.getSimpleName(),activity+" is not a valid activity name");
            }
        }
    }
}