package com.example.onroadservice;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import androidx.annotation.Nullable;


public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry1 = "create table users(name text,phone text,email text,password text,role text)";
        sqLiteDatabase.execSQL(qry1);
        String qry2="create table data(vehicle_type text,fuel_type text,requirement text,vehicle_no text,time text,location text)";
        sqLiteDatabase.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void register(String name,String phone,String email,String password){
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("phone",phone);
        cv.put("email",email);
        cv.put("password",password);
        //cv.put("role",role);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users",null,cv);
        db.close();
    }

    public void request_data(String vehicle_type,String fuel_type,String requirement,String vehicle_no,String time,String location)
    {
        ContentValues cv=new ContentValues();
        cv.put("vehicle_type",vehicle_type);
        cv.put("fuel_type",fuel_type);
        cv.put("requirement",requirement);
        cv.put("vehicle_no",vehicle_no);
        cv.put("time",time);
        cv.put("location",location);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("data",null,cv);
        db.close();
    }


    public int login(String email, String password, String role){
        int result = 0;
        String[] str = new String[2];
        str[0] = email;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where email=? and password=?",str);
        if(c.moveToFirst()){
            result =1;
        }
        return result;
    }
    public Cursor getData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users and Select * from data",null);

        return cursor;
    }
}
