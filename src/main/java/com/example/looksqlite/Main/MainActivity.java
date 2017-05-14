package com.example.looksqlite.Main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.looksqlite.AllFile.AllFileActivity;
import com.example.looksqlite.R;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

public class MainActivity extends Activity {
    private ListView listView;
    private Main_History_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);
        initview();
    }

    //获取布局
    private void initview() {
        listView = (ListView) findViewById(R.id.main_listview);
        setListView();
        BannerView banner = new BannerView(this, ADSize.BANNER, getString(R.string.appid), getString(R.string.BannerPosID));
        //设置广告轮播时间，为0或30~120之间的数字，单位为s,0标识不自动轮播
        banner.setRefresh(30);
        banner.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int i) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" +i);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });

        /* 发起广告请求，收到广告数据后会展示数据   */
        listView.addHeaderView(banner);
        banner.loadAD();
    }

    //给listview添加数据
    private void setListView() {
        adapter = new Main_History_Adapter(this);
        //给listview增加头布局
        TextView text = new TextView(this);
        text.setTextSize(15);
        text.setTextColor(Color.parseColor("#66ccff"));
        text.setText(getString(R.string.Main_OpenHistory));
//        listView.addHeaderView(text);
        listView.setAdapter(adapter);
    }

    //打开数据库的按钮事件
    public void OpenSqlite(View v) {
        Intent intent = new Intent(MainActivity.this, AllFileActivity.class);
        startActivity(intent);
    }

    //新建数据库的按钮事件
    public void NewSqlite(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("对不起，该功能暂未开通!");
        builder.setMessage(getString(R.string.MySelf));
        builder.setNegativeButton("关闭", null);
        builder.show();
    }
}
