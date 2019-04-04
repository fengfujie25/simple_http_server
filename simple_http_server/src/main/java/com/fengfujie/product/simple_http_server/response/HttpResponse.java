package com.fengfujie.product.simple_http_server.response;

import com.fengfujie.product.simple_http_server.enums.HttpProtocolEnum;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class HttpResponse {

    //版本号
    private String version;

    //状态code
    private int statusCode;

    //reason
    private String statusReason;

    //头信息
    private Map<String, String> headers;

    //状态标志
    private boolean flag;

    //异常信息
    private Exception exception;

    //服务端
    private OutputStream outputStreamServer;

    //客户端
    private OutputStream outputStreamClient;

    private PrintWriter printWriter;


    public HttpResponse(Socket socket) throws IOException {
        headers = new HashMap<>();
        outputStreamServer = new BufferedOutputStream(socket.getOutputStream());
        printWriter = null;
        flag = false;
        setHttpVersion(HttpProtocolEnum.HTTP11);
        statusCode = 200;
        statusReason = "OK";
    }

    public PrintWriter getPrintWriter() {
        if (outputStreamClient != null)
            throw new IllegalStateException("invalid getPrintWriter Exception : because output stream has already been called");
        if (printWriter == null) {
            sendHeaders();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStreamServer);
            printWriter = new PrintWriter(outputStreamWriter);
        }
        return printWriter;
    }


    public void setHeaders(Header server, String value) throws Exception {
        setHeaders(server.toString(), value);
    }

    public void setHeaders(String name, String value) throws Exception {
        checkHeadSent("Header");
        headers.put(name, value);
    }

    public void setContentType(String contentType) throws Exception {
        setHeaders(Header.CONTENTTYPE.toString(), contentType);
    }


    public OutputStream getOutPutStream() throws Exception {
        if (printWriter != null)
            throw new Exception("Invalid get outputStream.");
        if (outputStreamClient == null) {
            sendHeaders();
            outputStreamClient = outputStreamServer;
        }
        return outputStreamClient;
    }

    /**
     * 统一关闭所有流
     */
    public void close() {
        try {
            flush();
            if (printWriter != null) {
                printWriter.close();
            }
            if (outputStreamServer != null) {
                outputStreamServer.close();
            }
        } catch (Exception e) {
        }
    }

    public void flush() throws Exception{
        sendHeaders();
        if (printWriter != null) {
            printWriter.flush();
        }
        if (outputStreamServer != null) {
            outputStreamServer.flush();
        }
    }

    /**
     * 写headers
     */
    private void sendHeaders() {
        if (flag) return;
        flag = true;
        PrintWriter pw = new PrintWriter(outputStreamServer);
        pw.print(version);
        pw.print(' ');
        pw.print(statusCode);
        pw.print(' ');
        pw.print(statusReason);
        pw.print("\r\n");
        for (String key : headers.keySet()) {
            pw.print(key);
            pw.print(":");
            pw.print(headers.get(key));
            pw.print("\r\n");
        }
        pw.print("\r\n");
        pw.flush();
    }

    public void setHttpVersion(HttpProtocolEnum version) {
        try {
            checkHeadSent("HttpProtocol");
            this.version = version.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 返回头部信息枚举
     * 只列举了部分
     */
    public enum Header {
        /**
         * Accept-Ranges: bytes
         * Cache-Control: max-age=0
         * Connection: keep-alive
         * Date: Tue, 02 Apr 2019 09:59:20 GMT
         * ETag: W/"dac-169b4b1a598"
         * Expires: Tue, 02 Apr 2019 09:59:20 GMT
         * Last-Modified: Mon, 25 Mar 2019 11:52:15 GMT
         * P3P: policyref="/w3c/p3p.xml", CP="CUR ADM OUR NOR STA NID"
         * Server: QWS/1.0
         * Set-Cookie: QN1=Cl8cDlyjMnhJ7mfqNRDyAg==; expires=Thu, 31-Dec-37 23:55:55 GMT; domain=qunar.com; path=/
         * X-Powered-By: Express
         */
        SERVER("Server"),
        CONNECTION("Connection"),
        DATE("Date"),
        CONTENTLENGTH("Content-Length"),
        CONTENTTYPE("Content-Type"),
        LOCATION("Location");

        private String header;

        Header(String header) {
            this.header = header;
        }

        @Override
        public String toString() {
            return header;
        }
    }

    /**
     * 校验
     * @param message
     * @throws Exception
     */
    private void checkHeadSent(String message) throws Exception {
        if (flag)
            throw new IllegalStateException("Invalid now; headers already written/sent (use set " + message + " before calling getPrintWriter() or getOutputStream() )  [NOTE: Chained exception contains calling stack trace when getPrintWriter() or getOutputStream() was called]", exception);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) throws Exception {
        checkHeadSent("statusCode");
        this.statusCode = statusCode;
        if (statusCode != 200)
            this.statusReason = "NOT OK";
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public OutputStream getOutputStreamServer() {
        return outputStreamServer;
    }

    public void setOutputStreamServer(OutputStream outputStreamServer) {
        this.outputStreamServer = outputStreamServer;
    }

    public OutputStream getOutputStreamClient() {
        return outputStreamClient;
    }

    public void setOutputStreamClient(OutputStream outputStreamClient) {
        this.outputStreamClient = outputStreamClient;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
}
