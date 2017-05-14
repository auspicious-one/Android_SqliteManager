package com.example.looksqlite.Search;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by 查询弹出框
 */

public class SelectAllDialog implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private Activity activity;
    private LinearLayout root;
    private Spinner tablespinner, rowspinner, rowspinner2;
    private EditText whereedit;
    private TextView sqlText;
    private RadioButton group,low;
    private ArrayList<AllTableColumns> allList = new ArrayList<>();
    private ArrayList<String> tablename,rowname;            //存储表名的list
    private String stablename="",srowname="",srowname2="";           //获取已选中的下拉栏值和输入框值
    private String sqlinfo;     //获取最终的sql语句
    private boolean isGroup=true;
    public SelectAllDialog(Activity activity, ArrayList<AllTableColumns> allList) {
        this.activity = activity;
        this.allList = allList;
        newDialog();
    }

    //新建一个弹窗
    public void newDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //获取界面布局控件
        LayoutInflater inflater = LayoutInflater.from(activity);
        root = (LinearLayout) inflater.inflate(R.layout.sqlite_search_dialog, null);
        initview();
        setTableSpinner();
        builder.setTitle(activity.getString(R.string.sqlite_title));
        builder.setView(root);
        //设置查询按钮的点击事件
        builder.setNegativeButton(activity.getString(R.string.Menu_search), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(activity,SelectResult_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString(activity.getString(R.string.sqlinfo),sqlinfo);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        builder.setNeutralButton(activity.getString(R.string.sqlite_cancle), null);
        builder.setCancelable(false);
        builder.show();
    }

    //获取界面布局控件
    public void initview() {
        tablespinner = (Spinner) root.findViewById(R.id.sqlite_search_spinner_tablename);
        rowspinner = (Spinner) root.findViewById(R.id.sqlite_search_spinner_rowname);
        rowspinner2 = (Spinner) root.findViewById(R.id.sqlite_search_spinner_rowname2);
        tablespinner.setOnItemSelectedListener(this);
        rowspinner.setOnItemSelectedListener(this);
        rowspinner2.setOnItemSelectedListener(this);
        group=(RadioButton)root.findViewById(R.id.sqlite_search_group);
        low=(RadioButton)root.findViewById(R.id.sqlite_search_low);
        group.setOnClickListener(this);
        low.setOnClickListener(this);
        whereedit=(EditText)root.findViewById(R.id.sqlite_search_edittext);
        sqlText=(TextView)root.findViewById(R.id.sqlite_search_text);
        whereedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setSql();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //给tableSpinner设置下拉数据，即表名
    private void setTableSpinner() {
        tablename = new ArrayList<>();
        for (int i = 0; i < allList.size(); i++) {
            if (i > 0) {
                //判断前后两个数据是否相等,防止重复添加
                if (allList.get(i).getTablename() != allList.get(i - 1).getTablename()) {
                    tablename.add(allList.get(i).getTablename());
                }
            } else {
                tablename.add(allList.get(i).getTablename());
            }
        }
        //设置数组适配器
        ArrayAdapter tablenameadapter =
                new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_multiple_choice, tablename);
        tablespinner.setAdapter(tablenameadapter);
    }

    //给rowSpinner设置下拉数据，即列名
    private void setRowSpinner(String tablename) {
        rowname=new ArrayList<>();
        //如果表名相等，则存储列名
        for(AllTableColumns sqllistdata:allList){
            if(sqllistdata.getTablename().equals(tablename)){
                rowname.add(sqllistdata.getRowname());
            }
        }
        ArrayAdapter rownameadapter =
                new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_multiple_choice, rowname);
        rowspinner.setAdapter(rownameadapter);

        //同时给spinner2设置适配器和监听事件
        rowspinner2.setAdapter(rownameadapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.sqlite_search_spinner_tablename:
                stablename=tablename.get(position);
                setRowSpinner(tablename.get(position));
                setSql();
                break;
            case R.id.sqlite_search_spinner_rowname:
                srowname=rowname.get(position);
                whereedit.setHint(rowname.get(position)+"=?");
                setSql();
                break;
            case R.id.sqlite_search_spinner_rowname2:
                srowname2=rowname.get(position);
                group.setText("按"+rowname.get(position)+"升序排列");
                low.setText("按"+rowname.get(position)+"降序排列");
                setSql();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //生成sql语句
    public void setSql(){
        if(isGroup==true){
            sqlinfo="select * from "+stablename+sqlDao()+" order by "+srowname2;
        }else{
            sqlinfo="select * from "+stablename+sqlDao()+" order by "+srowname2+" desc";
        }
        sqlText.setText("Sql语句预览:\n"+sqlinfo);
    }
    //对输入框的值进行单独处理，防止带入空值
    public String sqlDao(){
        String where=null;
        if(whereedit.getText().toString()==null||whereedit.getText().toString().equals("")){
            where=" ";
        }else{
            where=" where "+srowname+" = "+whereedit.getText().toString();
        }
        return where;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sqlite_search_group:
                isGroup=true;
                setSql();
                break;
            case R.id.sqlite_search_low:
                isGroup=false;
                setSql();
                break;
        }
    }
}
