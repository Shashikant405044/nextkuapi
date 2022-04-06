package com.example.nextkul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.nextkul.databinding.ActivityWelcomeHomeBinding;

public class WelcomeHome extends AppCompatActivity {

    ActivityWelcomeHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityWelcomeHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkuserexistence();
        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                sp.edit().remove("username").commit();
                sp.edit().remove("password").commit();
                sp.edit().apply();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private void checkuserexistence() {


        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("username"))
            binding.uemail.setText(sp.getString("username",""));
        else
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}