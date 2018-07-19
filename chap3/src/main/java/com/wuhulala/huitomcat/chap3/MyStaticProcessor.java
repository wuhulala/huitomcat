
package com.wuhulala.huitomcat.chap3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 静态资源 处理器
 *
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/25
 */
public class MyStaticProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MyStaticProcessor.class);

    private final static String SUCCESS_HEADER = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "\r\n";

    private final static String ERROR_SERVER_HEADER = "HTTP/1.1 500 INTERNAL ERROR\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "\r\n";

    private final static String ERROR_NOT_FOUND_HEADER = "HTTP/1.1 404 FILE NOT FOUND\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "\r\n";

    private static final int BUFFER_SIZE = 2048;

    public void process(MyRequest request, MyResponse response) {
        sendHtml(request, response);
    }


    public static void sendHtml(MyRequest request, MyResponse response) {
        File file = new File(MyHTTPConstans.HTML_ROOT, request.getUri());
        logger.debug("process url[{}] use [{}]", request.getUri(), file.getAbsolutePath());
        try {
            if (file.exists()) {
                setHeader(response.getOut(), ERROR_SERVER_HEADER);
                copyFileToOut(file, response.getOut());
            }else {
                logger.error(">>>>>>>>>>>> 404 not found <<<<<<<<<<<");
                send404Page(response.getOut());
            }
        } catch (IOException e) {
            // do not
            logger.error(">>>>>>>>>>>> 404 not found <<<<<<<<<<<", e);
            send404Page(response.getOut());
        }

    }

    private static void setHeader(OutputStream output, String header) throws IOException {
        output.write(header.getBytes());
    }

    public static void send404Page(OutputStream out) {
        File file = new File(MyHTTPConstans.HTML_ROOT, MyHTTPConstans.PAGE_404);
        try {
            setHeader(out, ERROR_NOT_FOUND_HEADER);
            copyFileToOut(file,out);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>404 ERROR<<<<<<<<<<<");
        }
    }

    public static void send500Page(OutputStream out) {
        File file = new File(MyHTTPConstans.HTML_ROOT, MyHTTPConstans.PAGE_500);
        try {
            setHeader(out, ERROR_SERVER_HEADER);
            copyFileToOut(file, out);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>500 ERROR<<<<<<<<<<<");
        }
    }

    public static void copyFileToOut(File file, OutputStream out) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {

            byte[] buff = new byte[BUFFER_SIZE];

            int len = fis.read(buff);

            while (len != -1) {

                out.write(buff, 0, len);
                len = fis.read(buff);

            }
        }

    }
}
