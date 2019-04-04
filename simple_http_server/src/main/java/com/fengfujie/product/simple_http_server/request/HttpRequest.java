package com.fengfujie.product.simple_http_server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: fujie.feng
 * @Date: 2019-04-02
 */
public class HttpRequest {

    private String method;

    private String uri;

    private String version;

    private String body;

    private Map<String, String> headers;

    public HttpRequest(Socket socket) {

        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bf = null;
        try {
            inputStream = socket.getInputStream();
            reader = new InputStreamReader(inputStream);
            bf = new BufferedReader(reader);
            String requestLine;
            if ((requestLine = bf.readLine()) == null) {
                System.out.println("Http Request error or is null.");
                return;
            }
            //line 格式 GET /xxxxx HTTP/1.1
            try {
                String[] requestSplitArrays = requestLine.split("\\s");
                this.setMethod(requestSplitArrays[0]);
                this.setUri(requestSplitArrays[1]);
                if (requestSplitArrays.length >= 3) {
                    this.setVersion(requestSplitArrays[2]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //写header信息
            Map<String, String> maps = new HashMap<>();
            while (bf.ready()) {
                String line = bf.readLine();
                if (line == null || line.length() == 0) break;
                int key = line.indexOf(":");
                String requestHeaderKey = line.substring(0, key);
                String requestHeaderValue = line.substring(key + 1, line.length()).trim();
                maps.put(requestHeaderKey, requestHeaderValue);
                this.setHeaders(maps);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
