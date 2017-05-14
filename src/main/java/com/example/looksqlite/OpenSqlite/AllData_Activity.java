package com.example.looksqlite.OpenSqlite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.looksqlite.Application.DbPathApplication;
import com.example.looksqlite.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by显示单一表的具体列数据信息
 */

public class AllData_Activity extends Activity {
    private LinearLayout headColumns;
    private SQLiteDatabase db;
    private MyGridView gridView;
    private int numColumns = 0;       //表的列长度
    private ArrayList<SqliteListData> list = new ArrayList<>();        //存储表数据
    private ArrayList<AllDataUpdateList> columns = new ArrayList<>();        //存储列名
    private AllDataAdapter adapter;
    boolean isUpdate = false;
    private String tablename, dbpath;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alldata_main);
        initview();
    }

    //获取界面布局，获得传递过来的数据库文件对象和数据表名
    private void initview() {
        headColumns = (LinearLayout) findViewById(R.id.sqldata_main_head_lin);
        gridView = (MyGridView) findViewById(R.id.sqldat_main_girdview);
        progressDialog = new ProgressDialog(this);
        //设置列宽度
        gridView.setColumnWidth(100);
        tablename = getIntent().getStringExtra(getString(R.string.tablenamekey));
        //获取dbpath
        DbPathApplication dbPathApplication = (DbPathApplication) this.getApplicationContext();
        dbpath = dbPathApplication.getDbpath();
        progressDialog.setMessage(getString(R.string.Searching));
        progressDialog.show();
        progressDialog.setCancelable(false);
        start();
    }

    //开启线程查询数据库
    public void start() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SearchAllRows(dbpath, tablename);
            }
        };
        timer.schedule(task, new Date());
    }

    //查询数据库中的所有列
    public void SearchAllRows(String dbpath, String tablename) {
        db = SQLiteDatabase.openOrCreateDatabase(dbpath, null);
        if (tablename != null) {
            String sql1 = "SELECT * FROM " + tablename;
            Cursor cursor = db.rawQuery(sql1, null);
            numColumns = cursor.getColumnNames().length;
            gridView.setNumColumns(numColumns);
            for (int i = 0; i < numColumns; i++) {
                final Button button = new Button(this);
                button.setText(cursor.getColumnNames()[i]);
                button.setAllCaps(false);
                button.setBackgroundResource(R.drawable.sqldata_list_background);
                button.setWidth(250);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headColumns.addView(button);
                    }
                });
                columns.add(new AllDataUpdateList(i, cursor.getColumnNames()[i], tablename, db));
                //如果该表有数据,按照列名获得数据
                if (cursor.getCount() > 0) {
                    getAllData(tablename, cursor.getColumnNames()[i], i);
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list.size() > 0) {
                        adapter = new AllDataAdapter(list, AllData_Activity.this, columns, isUpdate);
                        gridView.setAdapter(adapter);
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    //查询数据库列中的所有数据,通过列名 str,并获得当前的列序号
    public void getAllData(String tablename, String rowname, int columnsnum) {
        Cursor cursor = db.query(tablename, new String[]{rowname}, null, null, null, null, null);
        int i = 0;
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            list.add(new SqliteListData((columnsnum + numColumns * i), cursor.getString(0)));
            i++;
        }
    }

    //修改按钮的点击事件
    public void Update(View v) {
        if (isUpdate == false) {
            isUpdate = true;
        } else {
            isUpdate = false;
        }
        adapter = new AllDataAdapter(list, this, columns, isUpdate);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    //返回按钮的点击事件
    public void Back(View v) {
        finish();
    }


}
