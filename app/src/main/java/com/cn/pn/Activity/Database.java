package com.cn.pn.Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    static  String databasename = "friendlist";
    static  int version = 1;
    SQLiteDatabase db,db1;


    public Database(Context context) {
        super(context, databasename, null, version);
        db = getWritableDatabase();
        db1 = getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists details(name text,email text,password text,mobile,vehicalno,licno,dob,address)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertnumber(String name, String email, String password, String mobile, String vehicalno, String licno, String dob, String address) {
        System.out.println("Insert   methoddddddddddddddddddddddddd");
        db.execSQL("insert or replace into details('name','email','password','mobile','vehicalno','licno','dob','address') values('"+name+"','"+email+"','"+password+"','"+mobile+"','"+vehicalno+"','"+licno+"','"+dob+"','"+address+"')");

    }

    public ArrayList<String> names()
    {
        ArrayList<String> data1=new ArrayList<String>();
        Cursor c=db1.rawQuery("select name from details", null);
        if(c!=null)
        {
            c.moveToFirst();

            while( !(c.isAfterLast()))
            {

                String 	 ss=c.getString(c.getColumnIndex("name"));

                data1.add(ss);
                c.moveToNext();

            }
        }
        c.close();
        return data1 ;

    }

    public ArrayList<String> numbers(String em,String pw)
    {
        ArrayList<String> data1=new ArrayList<String>();
        Cursor c=db1.rawQuery("select password from details", null);
        if(c!=null)
        {
            c.moveToFirst();

            while( !(c.isAfterLast()))
            {

                String 	 ss=c.getString(c.getColumnIndex("password"));

                data1.add(ss);
                c.moveToNext();

            }
        }
        c.close();
        return data1 ;

    }

}
