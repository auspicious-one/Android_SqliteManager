package com.example.looksqlite.AllFile;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by 自定义对包含实体类的list排序
 */

public class ComparatorListData implements Comparator<ListData> {
    @Override
    public int compare(ListData list1, ListData list2) {
        int flag = 0;
        //先对文件夹进行排序,再对文件进行排序
        if(list1.getFile().isDirectory()&&list2.getFile().isDirectory()){
            flag=Collator.getInstance(Locale.CHINA).compare(list1.getName(),list2.getName());
        }
        //不能直接else
        if(list1.getFile().isFile()&&list2.getFile().isFile()){
            flag=Collator.getInstance(Locale.CHINA).compare(list1.getName(),list2.getName());
        }
        return flag;
    }
}
