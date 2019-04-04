package com.fengfujie.product.simple_http_server.utils;

import com.fengfujie.product.simple_http_server.request.HttpRequest;
import com.fengfujie.product.simple_http_server.response.HttpResponse;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 输入打印类
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class PrintUitls {

    public static void printForDir(File childFile, File rootDir, HttpResponse response) throws Exception {
        StringBuilder builder = new StringBuilder("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "<meta charset=\"utf-8\">\n"
                + "<title>Directory listing for /</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h2>Directory listing</h2>\n"
                + "<hr>\n"
                + "<ul>");
        File[] files = rootDir.listFiles();
        for (File file : files) {
            String link = "<li><a href=" + getWebPath(file, childFile) + ">" + file.getName() + "<a/></li>\n";
            builder.append(link);
        }
        builder.append("</ul>\n"
                + "<hr>\n"
                + "</body>\n"
                + "</html>");
        String content = builder.toString();
        response.setHeaders(HttpResponse.Header.CONTENTLENGTH, Long.toString(content.length()));
        response.setContentType("text/html");
        OutputStream os = response.getOutPutStream();
        os.write(content.getBytes("utf-8"));
        os.close();
    }

    public static void printException(HttpResponse response, String message, Exception ex) {
        try {
            if (response.getStatusCode() == 200)
                response.setStatusCode(500);
            response.setContentType("text/html");
            PrintWriter printWriter = response.getPrintWriter();
            printWriter.println("<html>\n" +
                    "<head>\n" +
                    "<title>Server Error</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Server Error</h1>\n" +
                    "<h1>Error Code " + response.getStatusCode() + "</h1>\n<p>\n");
            printWriter.println(message);
            printWriter.println("</p><pre>\n");
            if (ex != null) {
                ex.printStackTrace(printWriter);
            }
            printWriter.println("</pre></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static String getWebPath(File file, File rootDir) throws Exception {
//        if (!rootDir.getPath().equals(".")) {
//            rootDir = new File(".");
//        }
        File root = new File(rootDir.getPath());
        String filePath = file.getCanonicalPath().replace(root.getCanonicalPath(), "");
        return filePath;
    }
}
