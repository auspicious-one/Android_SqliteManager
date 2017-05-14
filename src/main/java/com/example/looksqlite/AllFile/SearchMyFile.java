package com.example.looksqlite.AllFile;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;

import com.example.looksqlite.R;

import java.util.ArrayList;

/**
 * Created by 查找自己需要的文件
 */

public class SearchMyFile {
    private Activity activity;
    private LinearLayout linearLayout;

    public SearchMyFile(Activity activity) {
        this.activity = activity;
        newDialog();
    }

    public void newDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        linearLayout = (LinearLayout) inflater.inflate(R.layout.searchmyfile_dialog, null);
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) linearLayout.findViewById(R.id.multiAutoCompleteTextView);
        //获取所有文件的集合
        ArrayList<ListData> listDatas = SearchAllFile.getList(Environment.getExternalStorageDirectory().getAbsolutePath());
        //拿出所有文件名得到集合
        ArrayList<String> filenames = new ArrayList<>();
        for (ListData listData : listDatas) {
            filenames.add(listData.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line,filenames);
        textView.setAdapter(arrayAdapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        builder.setTitle("查找文件");
        builder.setView(linearLayout);
        builder.show();
    }
}
