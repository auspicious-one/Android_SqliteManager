package com.example.looksqlite.OpenSqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.looksqlite.Application.DbPathApplication;
import com.example.looksqlite.Main.MainTitleEvent;
import com.example.looksqlite.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 根据传递的数据库地址获取数据库全部表
 */

public class AllTable_Activity extends Activity{
    private ListView listView;
    private ArrayList<String> list=new ArrayList<>();         //存储查询到的表名
    private SQLiteDatabase db;
    private String dbname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alltable_main);
        initview();
        getDbPath();
        new MainTitleEvent(this);
    }
    //获取界面布局
    private void initview() {
        listView=(ListView)findViewById(R.id.alltable_listview);
    }
    //获取数据库地址
    private void getDbPath() {
        dbname=getIntent().getStringExtra(getString(R.string.dbnamekey));
        //将数据库地址存储到全局变量，减少传递的量
        DbPathApplication dbPathApplication= (DbPathApplication) this.getApplicationContext();
        dbPathApplication.setDbpath(dbname);
        SearchDataBase(dbname);
    }
    //根据传来的文件名查询数据库中的所有表
    public void SearchDataBase(String dbname) {
        File dbfile = new File(dbname);
        if (dbfile.exists()) {
            db = SQLiteDatabase.openOrCreateDatabase(dbname, null);
            //先查询数据库中的所有表，然后再布局上添加
            String sql = "select name,sql from sqlite_master where type='table' order by name";
            Cursor cursor = db.rawQuery(sql, null);
            list.add(0,dbname);         //存储数据表名
            int i=0;
            if (cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                    i++;
                    list.add(i,cursor.getString(0));
                }
            }
        }
        //为listview增加head，显示数据库文件位置
        TextView textView=new TextView(this);
        textView.setBackgroundResource(R.drawable.spinner_bk);
        textView.setText(getString(R.string.dbpath)+dbname);
        listView.addHeaderView(textView);
        listView.setAdapter(new AllTableAdapter(list,this));
    }
}
