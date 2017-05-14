package com.example.looksqlite.OpenSqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by 数据表适配器
 */

public class AllTableAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<String> list;
    public AllTableAdapter(ArrayList<String> list,Activity activity){
        this.list=list;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return list.size()-1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position+1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(activity);
        LinearLayout linearLayout= (LinearLayout) inflater.inflate(R.layout.alltable_list_view,null);
        TextView text= (TextView) linearLayout.findViewById(R.id.alltable_list_view_text);
        text.setText(list.get(position+1));
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,AllData_Activity.class);
                Bundle bundle=new Bundle();
                //传递数据库位置信息
                bundle.putString(activity.getString(R.string.dbnamekey),list.get(0));
                bundle.putString(activity.getString(R.string.tablenamekey),list.get(position+1));
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        return linearLayout;
    }
}
