package com.cn.pn.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ravi on 14/2/16.
 */

public class DBAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_USERNAME= "username";
    public static final String KEY_PASSWORD = "password";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "usersdb";
    private static final String DATABASE_TABLE = "users";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table users (_id integer primary key autoincrement, " + "username text not null, "+ "password text not null);";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }

    public void open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
    }

    public void close()
    {
        DBHelper.close();
    }

    public boolean AddUser(String username, String password)
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=? ", new String[]{username});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return false;
            }
        }

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);
        db.insert(DATABASE_TABLE, null, initialValues);

        return true;

    }

    public boolean Login(String username, String password) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }
    public boolean delUser(String username)
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{username});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                db.delete(DATABASE_TABLE,KEY_USERNAME+"="+"'"+username+"'",null);
                return true;
            }
        }

        return false;
    }
    public boolean updateuser(String username, String password)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PASSWORD, password);
        db.update(DATABASE_TABLE,initialValues,KEY_USERNAME+"="+"'"+username+"'",null);

        return true;

    }
    public Cursor selectuser(String username) throws SQLException
    {

        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                                KEY_PASSWORD},
                        KEY_USERNAME + "=" + "'"+username+"'",
                        null,
                        null,
                        null,
                        null,
                        null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor selectall()throws SQLException
    {
        Cursor mcur=db.rawQuery("select * from users", null);
        if(mcur!=null){
            mcur.moveToFirst();
        }
        return mcur;
    }

}