package com.wuhulala.chap1;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/19
 */
public class MyResponse {
    private OutputStream out;
    private MyRequest request;

    public MyResponse(OutputStream out) {
        this.out = out;
    }

    public void setRequest(MyRequest request) {
        this.request = request;
    }


    public void sendMessage() throws IOException {

        String message = "HTTP/1.1 200 OK \r\n" +
                "content-type:text/html\r\n" +
                "\r\n" +
                "<h1>Hello "+request.getUri()+"!</h1>" +
                "<h2>shutdown_command:/shutdown</h2>";

        out.write(message.getBytes());

    }
}
