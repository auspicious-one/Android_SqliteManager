package com.example.looksqlite.Application;

import android.app.Application;

/**
 * Created by 创建一个全局变量，存储数据表位置信息。
 */

public class DbPathApplication extends Application {
    private String dbpath;          //存储数据库文件地址

    public String getDbpath() {
        return dbpath;
    }

    public void setDbpath(String dbpath) {
        this.dbpath = dbpath;
    }
}
