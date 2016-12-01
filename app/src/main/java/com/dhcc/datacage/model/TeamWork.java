package com.dhcc.datacage.model;

/**
 * Created by pengbangqin on 16-11-10.
 */
public class TeamWork {
    String title;
    String content;
    String time;

    public TeamWork(String img, String content, String title) {
        this.time = time;
        this.content = content;
        this.title = title;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
