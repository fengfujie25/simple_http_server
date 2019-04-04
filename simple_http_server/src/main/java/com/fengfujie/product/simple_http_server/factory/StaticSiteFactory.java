package com.fengfujie.product.simple_http_server.factory;

import com.fengfujie.product.simple_http_server.enums.HttpMethodEunm;
import com.fengfujie.product.simple_http_server.handler.SimpleServlet;
import com.fengfujie.product.simple_http_server.request.HttpRequest;
import com.fengfujie.product.simple_http_server.response.HttpResponse;
import com.fengfujie.product.simple_http_server.utils.FileUtils;
import com.fengfujie.product.simple_http_server.utils.PrintUitls;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * 静态站点工场
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class StaticSiteFactory extends SimpleServlet {
    
    private File rootDir;

    private static Map<String, String> conTentTypeMap = new HashMap<>();

    /**
     * 简单理解几个处理文件类型
     */
    static {
        conTentTypeMap.put(".js", "application/javascript");
        conTentTypeMap.put(".css", "text/css");
        conTentTypeMap.put(".java", "text/x-java-source");
        conTentTypeMap.put(".xml", "application/xml");
    }
    
    public StaticSiteFactory(Socket socket, File file) {
        super(socket);
        rootDir = file;
    }

    @Override
    protected void handle(HttpRequest request, HttpResponse response) {
        try {
            if (!HttpMethodEunm.GET.toString().equals(request.getMethod())) {
                doPost(request, response);
                return;
            }
            doGet(request, response);
        } catch (Exception e) {
            PrintUitls.printException(response, "Server Exception : " + e.getMessage(), e);
        }
    }

    /**
     * post  请求
     * @param request
     * @param response
     * @throws Exception
     */
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception{
        try {
            URI uri = new URI(request.getUri());
            File file = new File(rootDir, uri.getPath());
            if (!file.exists()) {
                response.setStatusCode(404);
                print(response, "File not found, request uri = " + uri);
                return;
            }
            if (!file.canRead()) {
                response.setStatusCode(403);
                print(response, "File matched by requested URI is not readable");
                return;
            }
            if (file.isFile()) {
                FileUtils.handleFile(file, this.getContentType(file.getName().toLowerCase()),response);
            } else if (file.isDirectory()) {
                handleDir(file, response);
            } else {
                print(response, "not file, not directory.");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            response.setStatusCode(400);
            return;
        }
    }

    /**
     * get 请求
     * @param request
     * @param response
     * @throws Exception
     */
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        this.doPost(request, response);
    }

    /**
     * 目录处理
     * @param rootDir
     * @param response
     * @throws Exception
     */
    private void handleDir(File rootDir, HttpResponse response) throws Exception {
        File file = new File(rootDir.getAbsolutePath() + File.separator + "index.html");
        if (file.exists()) {
            //redirect
            response.setStatusCode(302);
            response.setHeaders(HttpResponse.Header.LOCATION, PrintUitls.getWebPath(file, rootDir));
        } else {
            //存在子目录
            PrintUitls.printForDir(this.rootDir, rootDir, response);
        }
    }

    /**
     * 获取content-type
     * @param fileName
     * @return
     */
    private String getContentType(String fileName) {
        if (fileName == null || fileName.length() == 0)
            return "";
        for (Map.Entry<String, String> entry : conTentTypeMap.entrySet()) {
            if (fileName.endsWith(entry.getKey()))
                return entry.getValue();
        }
        return new MimetypesFileTypeMap().getContentType(fileName);
    }

    private void print(HttpResponse response, String message) {
        PrintUitls.printException(response, message, null);
    }
    

}
