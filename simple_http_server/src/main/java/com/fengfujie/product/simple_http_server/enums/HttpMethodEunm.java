package com.fengfujie.product.simple_http_server.enums;

/**
 * http 请求类型枚举类
 * 这里只简单列举了 get 和 post
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public enum  HttpMethodEunm {

    GET("GET"),
    POST("POST");


    private String method;

    HttpMethodEunm(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }
}
