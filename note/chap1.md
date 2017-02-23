>备注：此书的版本为Tomcat4.0 ，还是原始的BIO连接方式，还没有看完 ，过不涉及NIO

##1 .HTTP请求与响应

###请求报文

```
GET /asd HTTP/1.1
Host: 127.0.0.1:8080
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Accept-Encoding: gzip, deflate, sdch, br
Accept-Language: zh-CN,zh;q=0.8,en;q=0.6
```

###响应

```
//response header  表示这是遵循http协议的
HTTP/1.1 200 OK
content-type:text/html


//response Body
<h1>Hello asd</h1>
<h2>shutdown_command:/shutdown</h2>
```

那么tomcat的作用就是接受**请求报文**并返回相应的**响应报文**。实现了这个功能就相当于实现主要功能。

##2 . 实现

在学习java的实时通信的时候我们使用的就是socket(套接字）实现的。开一个SocketServer类接受客户端请求，并且同时会生成一个socket来处理客户端的请求。接收到请求之后我们就可以socket的输出流进行返回报文。若想要浏览器识别，就要遵循http协议。

比如我们实现一个在浏览器地址输入127.0.0.1:8080/name 返回报文是 Hello name!


```
//MyHttpServer.java
package com.wuhulala.chap2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * http服务器类
 * 
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
                response.sendMessage();

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

```

```
//
package com.wuhulala.chap2;

import java.io.IOException;
import java.io.InputStream;

/**
 * 请求类，用于格式化请求报文
 * 
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/19
 */
public class MyRequest {
    private InputStream input;
    private String uri;

    public MyRequest(InputStream input) {
        this.input = input;
    }

    /**
     * 解析报文
     */
    public void parse() {
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

    public String getUri() {
        return uri;
    }
}

```

```
package com.wuhulala.chap2;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 响应类 用于输出响应报文
 *
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

```

##3. 总结
此例通过socket实现接受http请求。解析了tomcat接受请求的原理。那么我现在联想到了Tomcat通过NIO连接的本质。。。不知道尔等想到了吗


