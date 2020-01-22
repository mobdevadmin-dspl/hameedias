package com.datamation.swdsfa.model;


public class ContentItemBackups {
    private String date;
    private String fileName;
    private String size;
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ContentItemBackups(String date, String fileName, int icon ,String size) {
        super();
        this.date = date;
        this.fileName = fileName;
        this.icon = icon;
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
