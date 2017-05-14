package com.example.looksqlite.OpenSqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 赵吉祥 on 2017-03-13.
 */

public class AllDataUpdateList {
    private int id;
    private String column;
    private String tablename;
    private SQLiteDatabase database;

    public AllDataUpdateList(int id,String column,String tablename,SQLiteDatabase database){
        this.id=id;
        this.column=column;
        this.tablename=tablename;
        this.database=database;
    }
    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }
}
