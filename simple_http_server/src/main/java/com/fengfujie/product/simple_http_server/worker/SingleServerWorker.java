package com.fengfujie.product.simple_http_server.worker;

import com.fengfujie.product.simple_http_server.factory.IRequestHandlerFactory;
import com.fengfujie.product.simple_http_server.handler.BaseServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单个server worker 模式
 *
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class SingleServerWorker extends Thread {

    private final int port;

    private final int timeout;

    private ServerSocket serverSocket;

    private IRequestHandlerFactory requestHandlerFactory;

    public SingleServerWorker(int port, int timeout, IRequestHandlerFactory requestHandlerFactory) {
        this.port = port;
        this.timeout = timeout;
        this.requestHandlerFactory = requestHandlerFactory;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                //socket.setSoTimeout(timeout);
                BaseServlet hanler = requestHandlerFactory.createHandler(socket);
                this.handler(hanler);
            } catch (Exception e) {
                throw new RuntimeException("Unexpected problem during Socket listening Exception: ", e);
            }
        }
    }

    /**
     * 启动
     */
    @Override
    public  void start() {
        try {
            synchronized(this) {
                serverSocket = new ServerSocket(port);
                super.start();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected problem during Socket binding port Exception: ", e);
        }

    }

    /**
     * 停止，关闭serverSocket
     */
    public void terminate() {
        try {
            if (serverSocket != null)
                serverSocket.close();
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * handler run
     * 子类可以覆盖此方法
     * @param hanler
     */
    protected void handler(BaseServlet hanler) {
        hanler.run();
    }
}
