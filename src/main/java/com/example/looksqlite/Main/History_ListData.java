package com.example.looksqlite.Main;

/**
 * Created by 实体类存储打开的数据库历史信息
 */

public class History_ListData {
    public History_ListData(String dbname,String dbpath,String time){
        this.dbname=dbname;
        this.dbpath=dbpath;
        this.time=time;
    }
    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbpath() {
        return dbpath;
    }

    public void setDbpath(String dbpath) {
        this.dbpath = dbpath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String dbname;
    private String dbpath;
    private String time;
}
