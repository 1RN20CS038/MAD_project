package com.example.onroadservice;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
;
import com.google.android.gms.tasks.OnSuccessListener;


public class FisrtPageLogin extends AppCompatActivity {
    Button logout, back,submit;
    EditText v_num,time;
    Spinner veh_type,f_type,require;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    String vehicle_type,fuel_type,req;
    private Button btnGetLocation;
    private TextView tvLocation;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt_page_login);
        back = findViewById(R.id.clback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities1();
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities2();
            }
        });

        v_num=findViewById(R.id.v_num);
        time=findViewById(R.id.time);
        veh_type=findViewById(R.id.veh_typ);
        f_type=findViewById(R.id.fuel_typ);
        require=findViewById(R.id.req_typ);





        btnGetLocation = findViewById(R.id.btnGetLocation);
        tvLocation = findViewById(R.id.tvLocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FisrtPageLogin.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(FisrtPageLogin.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
                        tvLocation.setText(locationText);
                    }
                }
            }
        };

        veh_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicle_type = parent.getItemAtPosition(position).toString();
                // Use the selectedValue string as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });
        f_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fuel_type = parent.getItemAtPosition(position).toString();
                // Use the selectedValue string as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });
        require.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                req = parent.getItemAtPosition(position).toString();
                // Use the selectedValue string as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });

        submit=findViewById(R.id.data_submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String vehicle_type = veh_type.getSelectedItem().toString();
                String  fuel_type= f_type.getSelectedItem().toString();
                String req = require.getSelectedItem().toString();
                String vehicle_num = v_num.getText().toString();
                String timing = time.getText().toString();// Retrieve the selected value from the spinner
                String location=tvLocation.getText().toString();
                Database db = new Database(getApplicationContext(),"onroadservice",null,1);
                if (vehicle_type.length() == 0 || fuel_type.length() == 0 || req.length() == 0 || vehicle_num.length() == 0 || timing.length()==0||location.length()==0) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {

                        db.request_data(vehicle_type,fuel_type,req,vehicle_num,timing,location);
                        Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(FisrtPageLogin.this, MainActivityLogin.class));
                    }
                }
            });


        }




    private void getLocation() {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                String latitude = String.valueOf(location.getLatitude());
                                String longitude = String.valueOf(location.getLongitude());
                                String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
                                tvLocation.setText(locationText);
                            } else {
                                // Handle case where location is null
                            }
                        }
                    });
        } catch (SecurityException e) {
            // Handle exception gracefully and inform the user
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }




    private void switchActivities1() {
        Intent switchActivityIntent = new Intent(this, MainActivityLogin.class);
        startActivity(switchActivityIntent);
    }

    private void switchActivities2() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}
