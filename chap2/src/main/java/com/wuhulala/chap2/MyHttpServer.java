package com.wuhulala.chap2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/19
 */
public class MyHttpServer {
    //地址
    private static final String HTTP_HOST = "127.0.0.1";
    //端口
    private static final int HTTP_PORT = 8080;
    //停止服务器命令
    private static final String SHUTDOWN_COMMAND = "/shutdown";
    //html文件目录
    static final String HTML_ROOT = System.getProperty("user.dir")+ File.separator + "webroot";
    //默认欢迎页面
    static final String WELCOME_PAGE = "hello.html";
    //404 page
    static final String PAGE_404 = "error.html";

    public static void main(String[] args) {
        MyHttpServer server = new MyHttpServer();
        System.out.println(">>>>>>>>>启动服务器ing<<<<<<<<<<");

        server.await();
    }

    private void await() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(HTTP_PORT, 1, InetAddress.getByName(HTTP_HOST));
        } catch (IOException e) {
            System.out.println(">>>>>>>>>启动服务器异常<<<<<<<<<<");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(">>>>>>>>>启动服务器成功<<<<<<<<<<");

        boolean shutdown = false;
        while(!shutdown){
            Socket client = null;
            InputStream input = null;
            OutputStream out = null;

            try {
                client = serverSocket.accept();

                input = client.getInputStream();
                out = client.getOutputStream();

                MyRequest request = new MyRequest(input);
                request.parse();

                MyResponse response = new MyResponse(out);
                response.setRequest(request);
                response.sendHtml();

                client.close();

                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

            } catch (IOException e) {
                System.out.println(">>>>>>>>>关闭Socket异常<<<<<<<<<<");
                System.out.println(e.getMessage());
                System.out.println(">>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");

            }
        }

    }
}
