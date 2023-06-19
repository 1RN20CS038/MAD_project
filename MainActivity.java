package com.example.onroadservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button register,customerLogin,loginService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });
        customerLogin = findViewById(R.id.logincustomer);
        customerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities1();
            }
        });
        loginService = findViewById(R.id.loginserviceP);
        loginService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities2();
            }
        });
    }




    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this,MainActivityRegister.class);
        startActivity(switchActivityIntent);
    }
    private void switchActivities1() {
        Intent switchActivityIntent1 = new Intent(this,MainActivityLogin.class);
        startActivity(switchActivityIntent1);
    }
    private void switchActivities2() {
        Intent switchActivityIntent2 = new Intent(this,MainActivityServicePLogin.class);
        startActivity(switchActivityIntent2);
    }
}
