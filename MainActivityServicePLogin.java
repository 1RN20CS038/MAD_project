package com.example.onroadservice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivityServicePLogin extends AppCompatActivity {

    Button serviceloginback;


    EditText edemail,edPassword;
    Spinner edrole;
    String role;
    Button btn;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service_plogin);

        serviceloginback= findViewById(R.id.serviceloginback);
        serviceloginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });
        edPassword = findViewById(R.id.sploginpass);
        edemail = findViewById(R.id.loginmailsp);
        btn = findViewById(R.id.sploginsubmit);
        edrole = findViewById(R.id.spinnerRole);
        edrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = parent.getItemAtPosition(position).toString();
                // Use the selectedValue string as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edemail.getText().toString();
                String password = edPassword.getText().toString();
                Database db = new Database(getApplicationContext(),"onroadservice",null,1);
                if (email.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Please fill all details",Toast.LENGTH_SHORT).show();
                } else {
                    if (db.login(email, password, role) == 1) {
                        if (role.equals("Service Provider")) { // Check if the role is "customer"
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.apply();
                            startActivity(new Intent(MainActivityServicePLogin.this, serverNotification.class));
                        } else {
                            Toast.makeText(getApplicationContext(),"Invalid role. Please select 'customer'",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email and password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }



    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}
