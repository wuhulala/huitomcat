package com.wuhulala.huitomcat.chap3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * http 连接器
 *
 * @author xueaohui
 * @version 1.0
 * @date 2017/3/1
 */
public class HttpConnector implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnector.class);

    boolean stopped;
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(MyHTTPConstans.HTTP_PORT, 1, InetAddress.getByName(MyHTTPConstans.HTTP_HOST));
        } catch (IOException e) {
            logger.error("启动服务器错误:", e);
            System.exit(1);
        }
        while (!stopped) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                logger.error("接受客户端请求错误:", e);
                continue;
            }
            HttpProcessor processor = new HttpProcessor();
            processor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
