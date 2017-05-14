package com.example.looksqlite.Search;

/**
 * Created by 存储选择的数据库中的所有的表和列名
 */

public class AllTableColumns {
    private String tablename;
    private String rowname;

    public AllTableColumns(String tablename, String rowname) {
        this.tablename = tablename;
        this.rowname = rowname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getRowname() {
        return rowname;
    }

    public void setRowname(String rowname) {
        this.rowname = rowname;
    }
}
