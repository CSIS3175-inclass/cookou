package com.example.cookou;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookou.database.DatabaseHelper;
import com.example.cookou.databinding.FragmentLoginBinding;
import com.example.cookou.databinding.FragmentRegisterBinding;
import com.example.cookou.models.User;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.HashSet;
import java.util.Set;

public class FragmentLogin extends Fragment {
    AppBarLayout appBar;
    MaterialToolbar toolbar;
    Button btnLogin;
    EditText emailEditTxt, passwordEditTxt;
    FragmentLoginBinding binding;
    MainListener mainListener;
    DatabaseHelper databaseHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        appBar = binding.appbar;
        toolbar = binding.toolbar;
        btnLogin = binding.loginButton2;
        emailEditTxt = binding.edittextEmailLogin;
        passwordEditTxt = binding.edittextPasswordLogin;

        mainListener = (MainListener) getActivity();
        databaseHelper = mainListener.getDatabasehelper();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.switchTo("fragIdentification");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });
        return view;
    }

    private void authenticate() {
        String email = emailEditTxt.getText().toString();
        String password = passwordEditTxt.getText().toString();

        if(email.equals("")||password.equals("")){
            Toast.makeText(getContext(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
        }
        else if(!databaseHelper.authenticate(email,password)){
            Toast.makeText(getContext(), "Credentials are invalid.", Toast.LENGTH_SHORT).show();
        }
        else {
            User user = databaseHelper.getUser(email);
            if(user != null)
            {
                databaseHelper.updateActiveStatus(user);

                Set<String> categories = databaseHelper.getUserCategories(user.getId());
                Set<String> areas = databaseHelper.getUserAreas(user.getId());

                Toast.makeText(getContext(), "Welcome back!", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId",user.getId());
                editor.putString("userEmail",user.getEmail());
                editor.putStringSet("categoryList", categories);
                editor.putStringSet("areaList", areas);

                editor.apply();

                mainListener.goTo("RecipeMenuActivity");
            }
            else {
                Log.e(getTag(),"User couldn't be retrieved.");
            }
        }


    }

}
