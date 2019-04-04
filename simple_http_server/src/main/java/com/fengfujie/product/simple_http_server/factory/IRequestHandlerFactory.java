package com.fengfujie.product.simple_http_server.factory;

import com.fengfujie.product.simple_http_server.handler.BaseServlet;

import java.net.Socket;

/**
 * request 请求处理接口
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public interface IRequestHandlerFactory {

    /**
     * 创建容器
     * @param socket
     * @return
     */
    BaseServlet createHandler(Socket socket);
}
