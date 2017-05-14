package com.example.looksqlite.MoreFunction;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.looksqlite.Application.DbPathApplication;
import com.example.looksqlite.NewSql.NewSql_Activity;
import com.example.looksqlite.R;
import com.example.looksqlite.Search.SelectAllDialog;
import com.example.looksqlite.Search.SelectAllTableColumns;

/**
 * Created by 创建一个弹出框
 */

public class MoreFunction implements PopupMenu.OnMenuItemClickListener{
    private Activity activity;
    private View view;
    private String dbname;
    public MoreFunction(Activity activity,View view){
        this.activity=activity;
        this.view=view;
        //获取数据库对象
        DbPathApplication dbPathApplication= (DbPathApplication) activity.getApplicationContext();
        dbname=dbPathApplication.getDbpath();
        CreateMenu();
    }
    public void CreateMenu(){
        PopupMenu popupMenu=new PopupMenu(activity,view);
        popupMenu.getMenuInflater().inflate(R.menu.alltable_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent=new Intent();
        switch (item.getItemId()){
            case R.id.alltable_menu_add:
                intent.setClass(activity, NewSql_Activity.class);
                activity.startActivity(intent);
                break;
            case R.id.alltable_menu_delete:
                intent.setClass(activity, NewSql_Activity.class);
                activity.startActivity(intent);
                break;
            case R.id.alltable_menu_update:
                intent.setClass(activity, NewSql_Activity.class);
                activity.startActivity(intent);
                break;
            case R.id.alltable_menu_search:
                new SelectAllDialog(activity,new SelectAllTableColumns().AllData(dbname));
                break;
        }
        return true;
    }
}
