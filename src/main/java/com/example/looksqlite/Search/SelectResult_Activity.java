package com.example.looksqlite.Search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.looksqlite.NewSql.SqlSearch;
import com.example.looksqlite.R;

/**
 * Created by
 */

public class SelectResult_Activity extends Activity {
    private LinearLayout headColumns;
    private GridView gridView;
    private String sqlinfo;        //数据库地址，表名，sql语句

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectresult_main);
        initview();
        new SqlSearch(this, headColumns, gridView, sqlinfo);
    }

    //获取界面布局
    private void initview() {
        headColumns = (LinearLayout) findViewById(R.id.sqldata_main_head_lin);
        gridView = (GridView) findViewById(R.id.sqldat_main_girdview);
        gridView.setColumnWidth(200);
        sqlinfo = getIntent().getStringExtra(getString(R.string.sqlinfo));
    }
}
