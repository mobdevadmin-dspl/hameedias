package com.datamation.swdsfa.settings;

/**
 * Created by Rashmi
 */

public class ContentItem {
    private String name;
    private String desc;
    private int icon;

    public ContentItem(String n, String d,int i) {
        name = n;
        desc = d;
        icon =i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
