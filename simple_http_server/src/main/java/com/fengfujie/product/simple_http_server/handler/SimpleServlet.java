package com.fengfujie.product.simple_http_server.handler;


import com.fengfujie.product.simple_http_server.request.HttpRequest;
import com.fengfujie.product.simple_http_server.response.HttpResponse;
import com.fengfujie.product.simple_http_server.utils.DateUtils;

import java.net.Socket;
import java.util.Date;



/**
 * 简单servler容易
 * @Auther: fujie.feng
 * @Date: 2019-04-02
 */
public abstract class SimpleServlet extends BaseServlet {

    public SimpleServlet(Socket socket) {
        super(socket);
    }

    protected abstract void handle(HttpRequest request, HttpResponse response);

    @Override
    protected void handle(Socket socket) throws Exception {
        HttpRequest request = new HttpRequest(socket);
        HttpResponse response = new HttpResponse(socket);
        response.setHeaders(HttpResponse.Header.SERVER, "SimpleHttpServer/1.0");
        response.setHeaders(HttpResponse.Header.DATE, DateUtils.getDate());
        response.setHeaders(HttpResponse.Header.CONNECTION, "close");
        response.setVersion(request.getVersion());
        this.handle(request, response);
        //格式：127.0.0.1 - - [04/Apr/2019 06:04:03] "GET /sssss/json.txt HTTP/1.1" 200 -
        System.out.println(socket.getInetAddress().getHostAddress()+" - - [" + new Date().toString() + "] " + request.getMethod() + " " + request.getVersion() + " " + request.getUri() + " " + response.getStatusCode() + " -");
        response.close();
    }
}
