package com.example.looksqlite.Search;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by查询数据库中所有的数据文件，返回存储的表名和列名.这里的意图是因为主界面需要点击对应的表才能存入列信息，所有单独拿了出来查询所有
 */

public class SelectAllTableColumns {
    private SQLiteDatabase db;          ///声明数据库对象
    private ArrayList<AllTableColumns> AllListData = new ArrayList<>();       //存储所有表列信息
    //获取数据库名称
    public ArrayList<AllTableColumns> AllData(String dbname) {
        File dbfile = new File(dbname);
        if (dbfile.exists()) {
            db = SQLiteDatabase.openOrCreateDatabase(dbname, null);
            //先查询数据库中的所有表
            String sql = "select name,sql from sqlite_master where type='table' order by name";
            final Cursor cursor = db.rawQuery(sql, null);
            //如果数据库中有表数据
            if (cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                    SearchAllRows(cursor.getString(0));
                }
            }
        }
        return AllListData;
    }
    //查询数据库中的所有列
    public void SearchAllRows(String tablename) {
        if (tablename != null) {
            String sql1 = "SELECT * FROM " + tablename;
            Cursor cursor = db.rawQuery(sql1, null);
            for (int i = 0; i <cursor.getColumnNames().length;i++) {
                //存储数据表和数据列
                AllListData.add(new AllTableColumns(tablename,cursor.getColumnNames()[i]));
            }
        }
    }
}
