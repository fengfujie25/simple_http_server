package com.fengfujie.product.simple_http_server.enums;

/**
 * http 协议类型
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public enum  HttpProtocolEnum {

    HTTP10("HTTP/1.0"),
    HTTP11("HTTP/1.1");

    private String version;

    HttpProtocolEnum(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }
}
