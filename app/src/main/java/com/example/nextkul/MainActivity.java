package com.example.nextkul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nextkul.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    String called_from = getIntent().getStringExtra("called");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkuserexistence();
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginway();
            }
        });
    }

    private void checkuserexistence() {

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("username"))
            startActivity(new Intent(getApplicationContext(),WelcomeHome.class));
        else {
            binding.password.setTextColor(Color.RED);
        }
    }



    private void loginway() {

        String email= binding.email.getText().toString();
        String password=binding.password.getText().toString();
        Call<ResponseModel> call = RetrofitApi.getInstance(ApiName.class).verifyUser(email,password);
       call.enqueue(new Callback<ResponseModel>() {
         @Override
         public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

             ResponseModel responseModel =response.body();
             String output=responseModel.getMessage();
          //   Boolean status = responseModel.getSuccess();
             if(output.equals("failed"))
             {
                 binding.email.setText("");
                 binding.password.setText("");
                 binding.email.setError("Please Enter Email Valid");
                 binding.password.setError("Please Enter Password Valid");
             }


             if (output.equals("success"))
             {

                 if (binding.email.getText(). toString() .equals ("email") &&
                         binding.password.getText(). toString().equals ("password") ) {
                     Toast.makeText(MainActivity.this,"User and Passwword is correct",
                             Toast.LENGTH_SHORT).show();
                 }
                 SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                 SharedPreferences.Editor editor=sp.edit();
                 editor.putString("username", binding.email.getText().toString());
                 editor.putString("password",  binding.password.getText().toString());
                 editor.commit();
                 editor.apply();
                 Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(getApplicationContext(),WelcomeHome.class));

             }
         }

         @Override
         public void onFailure(Call<ResponseModel> call, Throwable t) {
             Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
         }
     });
    }

    public void creteaccount(View view) {

        startActivity(new Intent(getApplicationContext(),RagiterActivity.class));

    }
}