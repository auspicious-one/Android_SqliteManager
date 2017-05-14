package com.example.looksqlite.OpenSqlite;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 自定义GirdView
 */

public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //覆写isFocused方法,让GridView始终获得焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
