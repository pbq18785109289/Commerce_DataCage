package com.dhcc.datacage.model;

/**
 * Created by pengbangqin on 16-10-27.
 */
public class Law {
    String name;
    String desc;
    String time;
    String result;
    String place;

    public Law(String name, String desc, String time, String result, String place) {
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.result = result;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
