package com.wuhulala.huitomcat.chap3;

import jdk.management.resource.internal.inst.SocketInputStreamRMHooks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 客户端 请求处理器
 *
 * @author xueaohui
 * @version 1.0
 * @date 2017/3/1
 */
public class HttpProcessor {

    private static final Logger logger = LoggerFactory.getLogger(HttpProcessor.class);

    /**
     * 处理客户端请求方法
     *
     * @param socket
     */
    public void process(Socket socket) {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            MyRequest request = new MyRequest(input);
            MyResponse response = new MyResponse(output);

            parseRequest(input, output);
            parseHeaders(input);

            if (request.getUri().startsWith("/servlet")) {
                MyServletProcessor processor = new MyServletProcessor();
                processor.process(request, response);
            } else {
                MyStaticProcessor processor = new MyStaticProcessor();
                processor.process(request, response);
            }
        } catch (Exception e) {
            logger.error("处理客户端请求出错:", e);
        }
    }

    private void parseHeaders(InputStream input) {

    }

    private void parseRequest(InputStream input, OutputStream output) {

    }
}
