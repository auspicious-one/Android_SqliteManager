package com.example.looksqlite.MyDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.looksqlite.R;

import java.io.File;

/**
 * Created by 静态方式创建数据库.
 */

public class StaticDataBase {
    private SQLiteDatabase db;
    public SQLiteDatabase getDb(Context context){
        String dirname=context.getString(R.string.MyDataBaseDir);
        String filename=context.getString(R.string.MyDataBaseName);
        //创建数据库保存文件夹
        File file=new File(Environment.getExternalStorageDirectory()+"/"+dirname);
        if(!file.exists()){
            file.mkdir();
        }
        File dbfile=new File(file.getAbsolutePath()+"/"+filename);
        if(! dbfile.exists()){
            db = SQLiteDatabase.openOrCreateDatabase( dbfile.getAbsolutePath(), null);
            db.execSQL("CREATE TABLE OpenHistory(OpenHistoryId INTEGER PRIMARY KEY AUTOINCREMENT,OpenHistoryName varchar(20)," +
                    "OpenHistoryPath varchar(20),OpenHistoryTime varchar(20))");
        }else{
            db = SQLiteDatabase.openOrCreateDatabase( dbfile.getAbsolutePath(), null);
        }
        return db;
    }
}
