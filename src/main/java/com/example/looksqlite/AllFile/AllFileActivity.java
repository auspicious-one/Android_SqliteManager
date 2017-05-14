package com.example.looksqlite.AllFile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import com.example.looksqlite.R;

/**
 * Created by 所有文件的主Activity
 */

public class AllFileActivity extends Activity{
    private ListView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allfile_main);
        initview();
    }
    //获取界面布局
    public void initview(){
        gridView= (ListView) findViewById(R.id.allfile_listview);
        gridView.setAdapter(new AllFileAdapter(this, SearchAllFile.getList(Environment.getExternalStorageDirectory().getAbsolutePath())));
    }
    //界面查询按钮的点击事件，生成一个弹窗用来查询
    public void Search(View v){
        new SearchMyFile(this);
    }
    //界面的返回按钮点击事件
    public void Back(View v){
        finish();
    }
}
