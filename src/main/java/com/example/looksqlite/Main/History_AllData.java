package com.example.looksqlite.Main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.looksqlite.MyDataBase.DataBaseHelper;
import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by 查询数据库中的所有历史信息
 */

public class History_AllData {
    private Context context;
    private SQLiteDatabase db;
    private ArrayList<History_ListData> list=new ArrayList<>();
    public ArrayList<History_ListData> getList(Context context){
        this.context=context;
        db=new DataBaseHelper(context,context.getString(R.string.MyDataBaseName),null,1).getWritableDatabase();
//        db=new StaticDataBase().getDb(context);
        Select();
        return list;
    }

    private void Select() {
        String tablename=context.getString(R.string.MyTableName);
        String name=context.getString(R.string.HistoryName);
        String path=context.getString(R.string.HistoryPath);
        String time=context.getString(R.string.HistoryTime);
        //查询最近打开的历史数据
        Cursor cursor = db.query(tablename, new String[]{name,path,time}, null, null, null, null, time+" DESC limit 20");
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            list.add(new History_ListData(cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(path)),cursor.getString(cursor.getColumnIndex(time))));
        }
        cursor.close();
        db.close();
    }
}
