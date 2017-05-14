package com.example.looksqlite.OpenSqlite;

/**
 * Created by 存储当前数据要显示的位置,将数据准确加载到gridview
 */

public class SqliteListData {
    private int id;             //存储当前数据要显示的位置,将数据准确加载到gridview
    private String info;        //存储数据信息
    public SqliteListData(int id, String info){
        this.id=id;
        this.info=info;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
