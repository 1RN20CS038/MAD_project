package com.example.onroadservice;

import static android.opengl.ETC1.isValid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivityRegister extends AppCompatActivity {

    Button registerback,btn;
    EditText edname,edEmail,edPassword,edphone;
    //Spinner edrole;
    //String role;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        edPassword = findViewById(R.id.editTextText7);
        edname = findViewById(R.id.editTextText);
        edEmail = findViewById(R.id.editTextText6);
        edphone = findViewById(R.id.editTextText5);
        //edrole=findViewById(R.id.spinnerRole);
        btn = findViewById(R.id.registersubmit);
        //tv = findViewById(R.id.textViewExistingUser);
        /*edrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = parent.getItemAtPosition(position).toString();
                // Use the selectedValue string as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });*/

        registerback = findViewById(R.id.registerback);
        registerback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = edname.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String phone = edphone.getText().toString();
                //String role = edrole.getSelectedItem().toString(); // Retrieve the selected value from the spinner
                Database db = new Database(getApplicationContext(),"onroadservice",null,1);
                if (name.length() == 0 || password.length() == 0 || email.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (isValid(password)) {
                        db.register(name, phone, email, password);
                        Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivityRegister.this, MainActivityLogin.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, having a letter, digit, and special symbol", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    public  static boolean isValid(String passwordhere){
        int f1=0,f2=0,f3=0;
        if(passwordhere.length() < 8 ){
            return false;
        }else{

            for(int p = 0;p<passwordhere.length();p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;
                }
            }
            for(int r = 0 ;r < passwordhere.length();r++){
                if(Character.isDigit(passwordhere.charAt(r))){
                    f2 = 1;
                }
            }
            for(int s = 0; s < passwordhere.length();s++){
                char c = passwordhere.charAt(s);
                if(c>=33 && c<=46 || c == 64 ){
                    f3 = 1;
                }
            }
        }
        if(f1 == 1 && f2 == 1 && f3 == 1)
            return true;
        else
            return false;
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}