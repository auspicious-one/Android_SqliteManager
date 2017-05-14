package com.example.looksqlite.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.looksqlite.OpenSqlite.AllTable_Activity;
import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by数据适配器
 */

public class Main_History_Adapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<History_ListData> list = null;
    private ArrayList<String> AllName = new ArrayList<>();      //存储所有的数据库名

    public Main_History_Adapter(Activity activity) {
        this.activity = activity;
        getData();
    }

    //获取数据源
    public void getData() {
        list = new History_AllData().getList(activity);
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
        LayoutInflater inflater = LayoutInflater.from(activity);
        LinearLayout lin = (LinearLayout) inflater.inflate(R.layout.main_history_list_view, null);
        TextView name = (TextView) lin.findViewById(R.id.main_history_listview_name);
        TextView path = (TextView) lin.findViewById(R.id.main_history_listview_path);
        TextView time = (TextView) lin.findViewById(R.id.main_history_listview_time);
        ImageView img= (ImageView) lin.findViewById(R.id.main_history_list_imageView);
        img.setImageResource(R.drawable.sqlite2);
        //查询当前名称的数据库是否已经在数据库列表中显示，没有就显示
        if (AllName.size() > 0) {
            //遍历，查看当前数据是否已经显示
            for (String str : AllName) {
                if (!str.equals(list.get(position).getDbpath())) {
                    name.setText(list.get(position).getDbname());
                    path.setText(list.get(position).getDbpath());
                    time.setText(list.get(position).getTime());
                }
            }
            AllName.add(path.getText().toString());
        }
        //第一次直接存储
        else {
            name.setText(list.get(position).getDbname());
            path.setText(list.get(position).getDbpath());
            time.setText(list.get(position).getTime());
            AllName.add(path.getText().toString());
        }
        //设置点击事件，直接进入该文件对应的数据库
        lin.setId(position);
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开该数据库
                Intent intent=new Intent(activity, AllTable_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString(activity.getString(R.string.dbnamekey),list.get(v.getId()).getDbpath());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        return lin;
    }
}
