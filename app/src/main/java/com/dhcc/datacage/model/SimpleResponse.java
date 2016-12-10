package com.dhcc.datacage.model;

import java.io.Serializable;

/**
 * 如果只返回code 和msg 就把他强转成DhcResponse类型
 */
public class SimpleResponse implements Serializable {

    public String code;
    public String uid;

    public DhcResponse toDhcResponse() {
        DhcResponse lzyResponse = new DhcResponse();
        lzyResponse.code = code;
        lzyResponse.msg = uid;
        return lzyResponse;
    }
}