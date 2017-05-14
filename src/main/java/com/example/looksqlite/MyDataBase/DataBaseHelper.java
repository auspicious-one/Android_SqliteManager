package com.example.looksqlite.MyDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 创建本程序所需要的数据库
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个表保存打开的数据库历史
        db.execSQL("CREATE TABLE OpenHistory(OpenHistoryId INTEGER PRIMARY KEY AUTOINCREMENT,OpenHistoryName varchar(20)," +
                "OpenHistoryPath varchar(20),OpenHistoryTime varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }
}
