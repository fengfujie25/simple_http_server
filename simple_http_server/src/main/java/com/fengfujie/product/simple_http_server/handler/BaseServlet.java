package com.fengfujie.product.simple_http_server.handler;

import java.io.IOException;
import java.net.Socket;

/**
 * 容器基类
 * @Auther: fujie.feng
 * @Date: 2019-04-02
 */
public abstract class BaseServlet implements Runnable{

    private final Socket socket;

    public BaseServlet(Socket socket) {
        this.socket = socket;
    }

    protected abstract void handle(Socket socket) throws Exception;

    @Override
    public void run() {
        try {
            handle(socket);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
