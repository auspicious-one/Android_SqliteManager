package com.example.looksqlite.AllFile;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.looksqlite.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * Created by 根据文件属性设置缩略图,及文件大小
 */

public class FileImg {
    //设置缩略图
    public static void setImg(ArrayList<ListData> list, ImageView img, int position) {
        //判断是文件夹还是文件，显示相对应的缩略图
        if (list.get(position).getFile().isDirectory()) {
            img.setImageResource(R.drawable.filedir);
        } else {
            String name = list.get(position).getName();
            if (name.contains(".mp4")) {
                img.setImageBitmap(getVideoThumbnail(list.get(position).getFile().getAbsolutePath(), 100, 100, MINI_KIND));
            } else {
                img.setImageResource(R.drawable.file);
            }
        }
    }
    //设置文件大小
    public static void setLength(ArrayList<ListData> list, TextView text_length,int position){
        //如果是文件夹就显示还有多少项，否则显示文件长度
        if(list.get(position).getFile().isDirectory()){
            int size= SearchAllFile.getList(list.get(position).getPath()).size();
            text_length.setText(size+"项");
        }else{
            //显示具体地文件大小
            double length=list.get(position).getLength();
            double len;
            DecimalFormat df=new DecimalFormat("0.00");
            //如果文件大于1G
            if(length>=1024*1024*1024){
                len=length/(1024*1024*1024);
                text_length.setText(df.format(len)+"G");
            }
            //如果文件大于1M小于1G
            else if(length>1024*1024&&length<1024*1024*1024){
                len=length/(1024*1024);
                text_length.setText(df.format(len)+"M");
            }
            //如果文件大于1K小于1M
            else if(length>1024&&length<1024*1024){
                len=length/1024;
                text_length.setText(df.format(len)+"K");
            }
            //如果文件小于1K
            else{
                len=length;
                text_length.setText(df.format(len)+"B");
            }
        }
    }
    // 获取视频的缩略图
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        //extractThumbnail 方法二次处理,以指定的大小提取居中的图片,获取最终我们想要的图片
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
