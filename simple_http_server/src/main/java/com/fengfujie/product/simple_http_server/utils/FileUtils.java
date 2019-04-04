package com.fengfujie.product.simple_http_server.utils;

import com.fengfujie.product.simple_http_server.response.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件处理类
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class FileUtils {

    /**
     * 将文件内容以流的形式输出
     * @param file
     * @param response
     * @throws Exception
     */
    public static void handleFile(File file, String contentType, HttpResponse response) throws Exception {
        response.setContentType(contentType);
        long length = file.length();
        response.setHeaders(HttpResponse.Header.CONTENTLENGTH, Long.toString(length));
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            outputStream = response.getOutPutStream();
            int index;
            while ((index = fileInputStream.read()) != -1) {
                outputStream.write(index);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
