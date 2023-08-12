package com.example.cookou;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookou.database.DatabaseHelper;
import com.example.cookou.databinding.FragmentRegisterBinding;
import com.example.cookou.models.User;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

public class FragmentRegister extends Fragment {
    AppBarLayout appBar;
    MaterialToolbar toolbar;
    FragmentRegisterBinding binding;
    MainListener mainListener;
    Button btnRegister;
    DatabaseHelper databaseHelper;
    EditText emailEditTxt,passwordEditTxt,nameEditTxt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        appBar = binding.appbar;
        toolbar = binding.toolbar;
        emailEditTxt = binding.edittextEmailRegister;
        passwordEditTxt = binding.edittextPasswordRegister;
        nameEditTxt =binding.edittextName;
        btnRegister=binding.registerButton2;

        mainListener = (MainListener) getActivity();
        databaseHelper = mainListener.getDatabasehelper();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.switchTo("fragIdentification");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        return view;
    }

    private void registerUser() {
        String name = nameEditTxt.getText().toString();
        String email = emailEditTxt.getText().toString();
        String password = passwordEditTxt.getText().toString();

        if(name.equals("")||email.equals("")||password.equals("")){
            Toast.makeText(getContext(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
        }
        if(databaseHelper.userExits(email)){
            Toast.makeText(getContext(), "Email already used.", Toast.LENGTH_SHORT).show();
        }
        else {
            User user = new User(name,email,password,true);
            databaseHelper.insertUser(user);

            Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("App_preference_file", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId",user.getId());
            editor.putString("userEmail",user.getEmail());
            editor.apply();

            mainListener.goTo("SurveyActivity");
        }
    }

}
