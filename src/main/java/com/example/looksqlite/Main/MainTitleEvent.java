package com.example.looksqlite.Main;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.looksqlite.MoreFunction.MoreFunction;
import com.example.looksqlite.NewSql.NewSql_Activity;
import com.example.looksqlite.R;

/**
 * Created by 处理头文件
 */

public class MainTitleEvent implements View.OnClickListener{
    private Button back,sql,more;
    private Activity activity;
    public MainTitleEvent(Activity activity){
        this.activity=activity;
        intitview();
    }

    private void intitview() {
        back= (Button) activity.findViewById(R.id.main_head_back);
        sql= (Button) activity.findViewById(R.id.main_head_sql);
        more= (Button) activity.findViewById(R.id.main_head_more);
        back.setOnClickListener(this);
        sql.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_head_back:
                activity.finish();
                break;
            case R.id.main_head_sql:
                Intent intent=new Intent(activity, NewSql_Activity.class);
                activity.startActivity(intent);
                break;
            case R.id.main_head_more:
                new MoreFunction(activity,v);
                break;
        }
    }
}
