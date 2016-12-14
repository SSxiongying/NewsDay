package com.example.xy.newsday.a.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by xy on 2016/11/17.
 */
public class NesData {
    private String title;
    private String content;
    private String data;
    private String link;
    private String allPages;
    public String url;

    public NesData(String title, String content, String data, String link, String allPages,String url) {

        this.title = title;
        this.content = content;
        this.data = data;
        this.link=link;
        this.allPages=allPages;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAllPages() {
        return allPages;
    }

    public void setAllPages(String allPages) {
        this.allPages = allPages;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
