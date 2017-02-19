package com.wuhulala.chap1;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/19
 */
class MyRequest {
    private InputStream input;
    private String uri;

     MyRequest(InputStream input) {
        this.input = input;
    }

    /**
     * 解析报文
     */
     void parse() {
        StringBuffer request = new StringBuffer(2048);

        int i = 0;
        byte[] buffer = new byte[2048];

        try {
            i = input.read(buffer);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>>>解析HTTP协议错误<<<<<<<<<<<<<<<");
            System.out.println(e.getMessage());
        }

        for (int j = 0; j < i; j++) {
            request.append((char)buffer[j]);
        }
        System.out.println(">>>>>>>>>>>>>>解析HTTP协议结果<<<<<<<<<<<<<<<");
        System.out.println(request.toString());
        System.out.println(">>>>>>>>>>>>>>解析HTTP协议结果<<<<<<<<<<<<<<<");
        parseURI(request.toString());
    }

    /**
     * 通过请求解析URI
     *
     * @param request 请求字符串
     */
    private void parseURI(String request) {
        int index1, index2;
        index1 = request.indexOf(' ');
        if (index1 != -1) {
            index2 = request.indexOf(' ', index1 + 1);
            if(index2 > index1){
                uri = request.substring(index1+1,index2);
            }else{
                uri = "error";
            }
        }else{
            uri = "error";
        }
    }

    String getUri() {
        return uri;
    }
}
