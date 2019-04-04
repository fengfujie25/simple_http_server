package com.fengfujie.product.simple_http_server;


import com.fengfujie.product.simple_http_server.factory.IRequestHandlerFactory;
import com.fengfujie.product.simple_http_server.factory.RequestStaticSiteFactory;
import com.fengfujie.product.simple_http_server.worker.MultipleServerWorkers;

import java.io.File;

/**
 * 简单的http server
 *
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class SimpleHttpServer {

    private static final int DEFAILT_PROT = 8000;

    private static final File DEFAULT_FILE = new File(".");

    private final int port;

    private final File rootDir;

    private int maxThreads = 1;

    private int clientTimeoutMills = 1000;

    private MultipleServerWorkers serverWorkers;

    private boolean flag = false;

    public SimpleHttpServer() {
        this(DEFAILT_PROT, DEFAULT_FILE);
    }

    /**
     * 扩展：支持可配置端口和目录
     * @param port
     * @param rootDir
     */
    public SimpleHttpServer(int port, File rootDir) {
        this.port = port;
        this.rootDir = rootDir;
    }

    public void start() {
        if (!flag) {
            System.out.println("Server http on 0.0.0.0 port 8000 ......");
            IRequestHandlerFactory factory = new RequestStaticSiteFactory(rootDir);
            serverWorkers = new MultipleServerWorkers(port, clientTimeoutMills, maxThreads, factory);
            serverWorkers.start();
            flag = true;
        } else {
            throw new RuntimeException("Server is started. Http port = " + port + ", rootDir = " + rootDir.getAbsolutePath());
        }
    }

    public void stop() {
        if (flag) {
            serverWorkers.terminate();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        } else {
            System.out.println("Server not started. Http port = " + port + ", rootDir = " + rootDir.getAbsolutePath());
        }
    }

}
