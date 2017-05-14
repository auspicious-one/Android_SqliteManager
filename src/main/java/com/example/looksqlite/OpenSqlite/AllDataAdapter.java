package com.example.looksqlite.OpenSqlite;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by 表数据适配器
 */

public class AllDataAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<SqliteListData> list = null;
    private ArrayList<AllDataUpdateList> columns = null;
    private boolean isUpdata = false;
    private TextView text;
    private EditText editText;
    private LayoutInflater inflater;

    public AllDataAdapter(ArrayList<SqliteListData> list, Activity activity, ArrayList<AllDataUpdateList> columns, boolean isUpdata) {
        this.list = list;
        this.activity = activity;
        this.columns = columns;
        this.isUpdata = isUpdata;
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
        inflater = LayoutInflater.from(activity);
        if (isUpdata == false) {
            setText(position);
            return text;
        } else {
            setEditText(position);
            return editText;
        }
    }

    //显示所有数据
    public void setText(final int position) {
        text = (TextView) inflater.inflate(R.layout.alldata_list_view_text, null);
        //对list进行变量，如果
        for (SqliteListData listData : list) {
            if (listData.getId() == position) {
                text.setText(listData.getInfo());
            }
        }
        //通过新建一个弹窗来详细显示该数据列信息
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                for (SqliteListData listData : list) {
                    //把存储的数据要显示位置和当前position位置相对比
                    if (listData.getId() == position) {
                        builder.setMessage(listData.getInfo());
                    }
                }
                builder.show();
            }
        });
    }

    //设置为输入框更改数据库数据
    public void setEditText(final int position) {
        editText = (EditText) inflater.inflate(R.layout.alldata_list_view_edittext, null);
        //对list进行变量，如果
        for (SqliteListData listData : list) {
            if (listData.getId() == position) {
                editText.clearFocus();
                editText.setText(listData.getInfo());
                editText.setHint(listData.getInfo());
                //给当前显示的edit设置输入监听器
//                editText.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        int column = columns.size();
//                        //用显示的位置和列长度求余,余的值就是列名的位置
//                        int columnid = position % column;
//                        //根据列名的位置获得列名和表名
//                        String columnname=columns.get(columnid).getColumn();
//                        String tablename=columns.get(columnid).getTablename();
//                        SQLiteDatabase db=columns.get(columnid).getDatabase();
//                        String sql="update "+tablename+" set "+columnname +" = "+editText.getText().toString()+" where ";
//                        db.execSQL(sql);
//                        db.close();
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
            }
        }
    }
}
