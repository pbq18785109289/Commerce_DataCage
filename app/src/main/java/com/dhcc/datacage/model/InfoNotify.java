package com.dhcc.datacage.model;

/**
 * Created by pengbangqin on 2016/12/5.
 * 消息通知实体类
 */

public class InfoNotify {
    //通知标题
    String title;
    //通知的内容
    String message;
    //通知时间
    String time;

    public InfoNotify() {
    }

    public InfoNotify(String title, String message, String time) {
        this.title = title;
        this.message = message;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
