package com.wuhulala.chap2;

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


    public static void main(String[] args) {
        MyHttpServer server = new MyHttpServer();
        System.out.println(">>>>>>>>>启动服务器ing<<<<<<<<<<");

        server.await();
    }

    private void await() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(MyHTTPConstans.HTTP_PORT, 1, InetAddress.getByName(MyHTTPConstans.HTTP_HOST));
        } catch (IOException e) {
            System.out.println(">>>>>>>>>启动服务器异常<<<<<<<<<<");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(">>>>>>>>>启动服务器成功<<<<<<<<<<");

        boolean shutdown = false;
        while (!shutdown) {
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

                //如果是动态的请求 需
                if (request.getUri().startsWith("/servlet/")) {
                    MyServletProcessor processor = new MyServletProcessor();
                    processor.process(request,response);
                } else {
                    MyStaticProcessor processor = new MyStaticProcessor();
                    processor.process(request,response);
                }

                client.close();

                shutdown = request.getUri().equals(MyHTTPConstans.SHUTDOWN_COMMAND);

            } catch (IOException e) {
                System.out.println(">>>>>>>>>关闭Socket异常<<<<<<<<<<");
                System.out.println(e.getMessage());
                System.out.println(">>>>>>>>>>>>>><<<<<<<<<<<<<<<<<");

            }
        }

    }
}
