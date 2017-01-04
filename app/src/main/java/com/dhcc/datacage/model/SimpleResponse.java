package com.dhcc.datacage.model;

import java.io.Serializable;

/**
 * 如果只返回code 和msg 就把他强转成DhcResponse类型
 */
public class SimpleResponse implements Serializable {

    public String code;
    public String msg;

    public DhcResponse toDhcResponse() {
        DhcResponse dhcResponse = new DhcResponse();
        dhcResponse.code = code;
        dhcResponse.msg = msg;
        return dhcResponse;
    }
}