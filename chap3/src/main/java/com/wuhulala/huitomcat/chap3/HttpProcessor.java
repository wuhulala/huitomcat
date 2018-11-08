package com.wuhulala.huitomcat.chap3;

import com.alibaba.fastjson.JSON;
import jdk.management.resource.internal.inst.SocketInputStreamRMHooks;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端 请求处理器
 *
 * @author xueaohui
 * @version 1.0
 * @date 2017/3/1
 */
public class HttpProcessor {

    private static final Logger logger = LoggerFactory.getLogger(HttpProcessor.class);
    MyRequest request;
    MyResponse response;
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

            request = new MyRequest(input);
            response = new MyResponse(output);
            parseHeaders(input);

            parseRequest(input, output);

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

    /**
     * 处理报文转化为String
     */
    private String parseToString(InputStream input) {

        StringBuffer requestString = new StringBuffer(2048);

        int i = 0;
        byte[] buffer = new byte[2048];

        try {
            i = input.read(buffer);
        } catch (IOException e) {
            logger.error(">>>>>>>>>>>>>>解析HTTP协议错误<<<<<<<<<<<<<<<");
            logger.error(e.getMessage());
        }

        for (int j = 0; j < i; j++) {
            requestString.append((char) buffer[j]);
        }
        logger.debug(">>>>>>>>>>>>>>解析HTTP协议结果<<<<<<<<<<<<<<<");
        logger.debug(request.toString());
        logger.debug(">>>>>>>>>>>>>>解析HTTP协议结果<<<<<<<<<<<<<<<");
        return requestString.toString();
    }

    /**
     * 处理请求头
     *
     * @param input
     */
    private void parseHeaders(InputStream input) {
        String requestString = parseToString(input);

        String[] headers =  requestString.split("\r\n");

        logger.debug("解析结果------------"+JSON.toJSON(headers));


    }

    /**
     * 处理请求
     *
     * @param input
     * @param output
     */
    private void parseRequest(InputStream input, OutputStream output) {

    }
}
