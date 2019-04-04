package com.fengfujie.product.simple_http_server;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.InputStream;

@SpringBootApplication
public class SimpleHttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleHttpServerApplication.class, args);
        int port = 8000;
        String root = ".";

        if (args.length > 0) {
            if (StringUtils.isNumeric(args[0]))
                port = Integer.parseInt(args[0]);
            else
                System.out.println("输入端口号错误，默认启用8000端口");

        }
        if (args.length >= 2) {
            root = args[1];
        }
        File file = new File(root);

        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(port, file);
        simpleHttpServer.start();
    }

}
