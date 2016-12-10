package com.dhcc.datacage.model;

import java.io.Serializable;

/**
 * Created by pengbangqin on 2016/12/10.
 * 服务器返回所有对象的基类
 */

public class DhcResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public String code;
    public String msg;
    public T data;
}