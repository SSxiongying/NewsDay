package com.example.xy.newsday.a.bean;

/**
 * Created by xy on 2016/11/21.
 */
public class HotsData {
    private String title;
    private String content;
    private String data;

    public HotsData(String title, String content, String data) {
        this.title = title;
        this.content = content;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
