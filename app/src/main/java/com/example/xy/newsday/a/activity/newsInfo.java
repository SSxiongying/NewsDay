package com.example.xy.newsday.a.activity;

/**
 * Created by xy on 2016/11/15.
 */
public class newsInfo {
    private int icon;
    private String iconName;

    public newsInfo(int icon, String iconName) {
        this.icon = icon;
        this.iconName = iconName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
