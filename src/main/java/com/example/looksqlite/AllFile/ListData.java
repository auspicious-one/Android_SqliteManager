package com.example.looksqlite.AllFile;

import java.io.File;

/**
 * 实体类，存储文件，文件名，文件地址，文件长度
 */

public class ListData {
    private File file;
    private String name;
    private String path;
    private long length;

    public ListData(File file, String name, String path, long length) {
        this.file=file;
        this.name=name;
        this.path=path;
        this.length=length;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
