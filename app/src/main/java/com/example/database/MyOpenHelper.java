package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "mybrower";
    public static final String TABLE_HIS = "history";
    public static final String TABLE_BM = "bookmarks";
    private Context mycContext;

    public MyOpenHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, DB_NAME, factory, version);
        // TODO Auto-generated constructor stub
        mycContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table " + TABLE_HIS + "(_id integer"
                + "primary key autonicrement," + "title varchar(50),"
                + "url varchar(255)," + "time varchar(255))");
        db.execSQL("create table " + TABLE_BM + "(_id integer"
                + "primary key autonicrement," + "title varchar(50),"
                + "url varchar(255)," + "time varchar(255))");
        //Toast.makeText(mycContext, "数据库创建成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}