package com.dhcc.datacage.model;

import java.io.Serializable;

/**
 * Created by pengbangqin on 2016/12/10.
 * 用户对象
 */

public class UserModel implements Serializable{

    public String uid;
    public String uAddress;

    @Override
    public String toString() {
        return "UserModel{" +
                "uid='" + uid + '\'' +
                ", uAddress='" + uAddress + '\'' +
                '}';
    }
}
