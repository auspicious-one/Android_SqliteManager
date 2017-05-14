package com.example.looksqlite.NewSql;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.looksqlite.R;

/**
 * Created by 书写sql语句操作的界面
 */

public class NewSql_Activity extends Activity {
    private EditText sqlText;
    private LinearLayout headColumns;                //查询的列名
    private GridView gridView;
    private String sqlinfo;                   //用户输入的sql语句,和数据库地址对象,和数据库异常信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsql_main);
        initview();
    }

    //获得界面布局
    private void initview() {
        sqlText = (EditText) findViewById(R.id.newsql_main_edittext);
        headColumns = (LinearLayout) findViewById(R.id.newsql_main_columns);
        gridView = (GridView) findViewById(R.id.newsql_main_gridview);
        gridView.setColumnWidth(200);
    }
    //运行按钮的点击事件
    public void Search(View v) {
        //获取用户输入的sql语句
        if (sqlText.getText().toString().length() > 0) {
            sqlinfo = sqlText.getText().toString();
            new SqlSearch(this, headColumns, gridView, sqlinfo);
        }
    }
    //返回按钮的点击事件
    public void Back(View v) {
        finish();
    }
}
