package com.example.looksqlite.AllFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 静态类存储查询到的文件信息
 */

public class SearchAllFile {
    //存储查询到的文件信息
    public static ArrayList<ListData> getList(String str) {
        ArrayList<ListData> list = new ArrayList<>();
        File file = new File(str);
        //判断该file是文件还是文件夹,是文件夹就显示其下所有文件
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            //判断文件集合是否为空
            if(files!=null){
                //如果是文件夹先添加
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        list.add(new ListData(files[i], files[i].getName(), files[i].getAbsolutePath(), files[i].length()));
                    }
                }
                //文件夹添加后再添加文件
                for (int i = 0; i < files.length; i++) {
                    //如果是文件夹先添加
                    if (files[i].isFile()) {
                        list.add(new ListData(files[i], files[i].getName(), files[i].getAbsolutePath(), files[i].length()));
                    }
                }
            }
        } else {
            return null;
        }
        //对list进行排序
        Comparator<ListData> cmp = new ComparatorListData();
        Collections.sort(list, cmp);
        return list;
    }
}
