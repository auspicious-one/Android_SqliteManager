package com.example.looksqlite.AllFile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.looksqlite.MyDataBase.DataBaseHelper;
import com.example.looksqlite.OpenSqlite.AllTable_Activity;
import com.example.looksqlite.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 赵吉祥 on 2017-01-27.
 */

public class AllFileAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private Activity activity;
    private ArrayList<ListData> list;
    private Spinner spinner;         //获取当前地址概要
    private ArrayList<String> spinnerlist;
    private ArrayAdapter<String> spinneradapter;
    private SQLiteDatabase db;
    String lastpath;

    public AllFileAdapter(Activity activity, ArrayList<ListData> list) {
        this.activity = activity;
        this.list = list;
        initview();
    }

    //获得主布局控件
    public void initview() {
        spinner = (Spinner) activity.findViewById(R.id.allfile_main_spinner);
        spinnerlist = new ArrayList<>();
        spinnerlist.add(Environment.getExternalStorageDirectory().getAbsolutePath());
        spinneradapter = new ArrayAdapter<String>(activity, R.layout.myspinner, spinnerlist);
        spinner.setAdapter(spinneradapter);
        spinner.setOnItemSelectedListener(this);
        lastpath = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        RelativeLayout cardView = (RelativeLayout) inflater.inflate(R.layout.allfile_adapter_view, null);
        ImageView img = (ImageView) cardView.findViewById(R.id.allfile_adapter_img);
        TextView text_name = (TextView) cardView.findViewById(R.id.allfile_adapter_name);
        TextView text_length = (TextView) cardView.findViewById(R.id.allfile_adapter_length);
        text_name.setText(list.get(position).getName());
        //设置Item的点击事件
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getFile().isDirectory()) {
                    lastpath = list.get(position).getFile().getParentFile().getAbsolutePath();
                    spinnerlist.add(0, "/" + list.get(position).getName());
                    spinneradapter.notifyDataSetChanged();
                    list = SearchAllFile.getList(list.get(position).getPath());
                    refresh();
                } else{
                    String filename=list.get(position).getName();
                    //判断该文件是不是以db为后缀的数据库文件
                    if(filename.contains(".db")){
                        //将该db数据库文件的跟地址传递到打开数据库的class中
                        Intent intent=new Intent(activity, AllTable_Activity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString(activity.getString(R.string.dbnamekey),list.get(position).getFile().getAbsolutePath());
                        intent.putExtras(bundle);
                        //获取数据库对象，存储打开的db数据库
                        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                        db=new DataBaseHelper(activity,activity.getString(R.string.MyDataBaseName),null,1).getWritableDatabase();
//                        db= new StaticDataBase().getDb(activity);
                        ContentValues values=new ContentValues();
                        values.put(activity.getString(R.string.HistoryName),filename);
                        values.put(activity.getString(R.string.HistoryPath),list.get(position).getFile().getAbsolutePath());
                        values.put(activity.getString(R.string.HistoryTime),format.format(new Date()));
                        db.insert(activity.getString(R.string.MyTableName),null,values);
                        db.close();
                        activity.startActivity(intent);
                    }else{
                        Toast.makeText(activity, activity.getString(R.string.cannotsql), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        FileImg.setLength(list, text_length, position);      //显示文件长度及文件夹大小
        FileImg.setImg(list, img, position);      //显示文件及文件夹缩略图
        return cardView;
    }

    //刷新adapter
    public void refresh() {
        this.notifyDataSetChanged();
    }

    //按下返回键返回文件的上一级目录,没有就关闭当前Activity
    public AllFileAdapter() {
       /* if(!lastpath.equals("/mnt/sdcard/")){
            list = SearchAllFile.getList(lastpath);
            refresh();
        } else {
            activity.finish();
        }*/
        list = SearchAllFile.getList(lastpath);
        refresh();
    }

    //显示路径
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        StringBuilder path = new StringBuilder();
        if (position != 0) {
            //设置所选路径
            for(int i=(spinnerlist.size()-1);i>=position;i--){
//            for (int i = position; i < spinnerlist.size(); i++) {
                path.append(spinnerlist.get(i));
            }
            //删除已选择的路径之外路径
            for (int i = position; i < spinnerlist.size(); i++) {
                spinnerlist.remove(i - 1);
                spinneradapter.notifyDataSetChanged();
            }
            list = SearchAllFile.getList(path.toString());
            refresh();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}
