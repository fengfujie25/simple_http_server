package com.fengfujie.product.simple_http_server.worker;

import com.fengfujie.product.simple_http_server.factory.IRequestHandlerFactory;
import com.fengfujie.product.simple_http_server.handler.BaseServlet;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.TimeUnit;

/**
 * 多个workers模式
 *
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class MultipleServerWorkers extends SingleServerWorker {

    private final ExecutorService executorService;

    public MultipleServerWorkers(int port, int timeout, int threads, IRequestHandlerFactory requestHandlerFactory) {
        super(port, timeout, requestHandlerFactory);
        executorService = Executors.newFixedThreadPool(threads);

    }

    @Override
    public void terminate() {
        System.out.println("Is stopping http server.");
        super.terminate();
        executorService.shutdown();
        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert executorService.isTerminated();
        assert executorService.isShutdown();
    }

    @Override
    public void handler(BaseServlet hanler) {
        assert hanler != null;
        executorService.execute(hanler);
    }
}
