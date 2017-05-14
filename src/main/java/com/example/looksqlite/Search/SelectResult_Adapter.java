package com.example.looksqlite.Search;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.looksqlite.OpenSqlite.SqliteListData;
import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by 详细数据显示的适配器
 */

public class SelectResult_Adapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<SqliteListData> list=null;
    public SelectResult_Adapter(ArrayList<SqliteListData> list, Activity activity){
        this.list=list;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(activity);
        TextView text= (TextView) inflater.inflate(R.layout.alldata_list_view_text,null);
        //对list进行遍历,如果存储的数据位置和当前position位置相等就添加
        for(SqliteListData listData:list){
            if(listData.getId()==position){
                text.setText(listData.getInfo());
            }
        }
        text.setId(position);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                for(SqliteListData listData:list){
                    if(listData.getId()==v.getId()){
                        builder.setMessage(listData.getInfo());
                    }
                }
                builder.show();
            }
        });
        return text;
    }
}
