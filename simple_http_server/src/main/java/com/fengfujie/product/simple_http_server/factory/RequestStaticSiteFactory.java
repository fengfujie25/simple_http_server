package com.fengfujie.product.simple_http_server.factory;

import com.fengfujie.product.simple_http_server.handler.BaseServlet;

import java.io.File;
import java.net.Socket;

/**
 * 静态站点请求处理工厂
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class RequestStaticSiteFactory implements IRequestHandlerFactory {

    private File rootDir;

    public RequestStaticSiteFactory(File rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    public BaseServlet createHandler(Socket socket) {
        return new StaticSiteFactory(socket, rootDir);
    }
}
