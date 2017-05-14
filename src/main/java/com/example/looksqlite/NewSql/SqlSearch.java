package com.example.looksqlite.NewSql;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.looksqlite.Application.DbPathApplication;
import com.example.looksqlite.OpenSqlite.SqliteListData;
import com.example.looksqlite.R;
import com.example.looksqlite.Search.SelectResult_Adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 通过sql语句查询信息,供两个activity使用
 */

public class SqlSearch {
    private SQLiteDatabase db;
    private GridView gridView;
    private String dbpath, sqlinfo;
    private Activity activity;
    private LinearLayout headColumns;
    private SelectResult_Adapter adapter;
    private ArrayList<SqliteListData> list = new ArrayList<>();
    private AlertDialog.Builder dialog;
    int numColumns;

    public SqlSearch(Activity activity, LinearLayout headColumns, GridView gridView, String sqlinfo) {
        this.activity = activity;
        this.headColumns = headColumns;
        this.gridView = gridView;
        this.sqlinfo = sqlinfo;
        initview();
        Search();
    }

    private void initview() {
        DbPathApplication dbPathApplication = (DbPathApplication) activity.getApplicationContext();
        dbpath = dbPathApplication.getDbpath();
        dialog = new AlertDialog.Builder(activity);
        dialog.setNegativeButton(activity.getString(R.string.Yes), null);
    }

    //开启一个线程查询
    public void Search() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SearchAllRows();
            }
        };
        timer.schedule(task, new Date());
    }

    //查询数据库中的所有列
    public void SearchAllRows() {
        try {
            db = SQLiteDatabase.openOrCreateDatabase(dbpath, null);
            Cursor cursor = db.rawQuery(sqlinfo, null);
            numColumns = cursor.getColumnNames().length;
            //如果是查找到数据
            if (numColumns > 0) {
                //先清除上次查询到的列数据
                if (headColumns != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headColumns.removeAllViews();
                        }
                    });
                }
                //再循环添加查找到的数据列信息
                for (int i = 0; i < numColumns; i++) {
                    final Button button = new Button(activity);
                    button.setText(cursor.getColumnNames()[i]);
                    button.setBackgroundResource(R.drawable.sqldata_list_background);
                    button.setWidth(250);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headColumns.addView(button);
                        }
                    });
                }
                //如果该表有数据，就显示到界面，并清除上次查询数据
                if (cursor.getCount() > 0) {
                    //清除上次查询信息
                    if (list != null && adapter != null) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                    }
                    getAllData(cursor);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list.size() > 0) {
                                gridView.setNumColumns(numColumns);
                                adapter = new SelectResult_Adapter(list, activity);
                                gridView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
            //如果未查找到数据，使用exexSql执行sql语句
            else {
                db.execSQL(sqlinfo);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage(activity.getString(R.string.RunSqlSuccess));
                        dialog.show();
                    }
                });
            }
        } catch (SQLiteException e) {
            final String message = e.toString();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(message);
                    dialog.show();
                }
            });
        }
    }

    //查询数据库列中的所有数据,通过列名 str,并获得当前的列序号
    public void getAllData(Cursor cursor) {
        int id = 0;   //j控制列，id控制数目
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnNames().length; i++) {
                list.add(new SqliteListData(id, cursor.getString(i)));
                id++;
            }
        }
        //执行完毕后关闭数据源
        cursor.close();
        db.close();
    }
}
