package com.example.onroadservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class serverNotification extends AppCompatActivity {
  RecyclerView recyclerView;
  ArrayList<String > pass,email;
  Database db;
  myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_notification);
        db = new Database(getApplicationContext(),"onroadservice",null,1);
        email=new ArrayList<>();
        pass=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        adapter=new myAdapter(this,email,pass);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(serverNotification.this));
        displayData();
    }

    private void displayData() {
        Cursor cursor=db.getData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(serverNotification.this,"No entry exists",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext())
            {
                email.add(cursor.getString(1));
                pass.add(cursor.getString(2));
            }
        }
    }
}