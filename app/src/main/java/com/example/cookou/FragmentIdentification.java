package com.example.cookou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookou.databinding.FragmentIdentificationBinding;

public class FragmentIdentification extends Fragment {
    private FragmentIdentificationBinding binding;
    Button btnLogin;
    Button btnRegister;
    MainListener mainListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIdentificationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        btnLogin = binding.loginButton1;
        btnRegister = binding.registerButton1;
        mainListener = (MainListener) getActivity();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.switchTo("fragLogin");
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainListener.switchTo("fragRegister");
            }
        });


        return view;
    }
}
