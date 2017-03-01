
package com.wuhulala.huitomcat.chap3;

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
    private static final int BUFFER_SIZE = 2048;

    public void process(MyRequest request, MyResponse response) {
        sendHtml(request,response);
    }


    public void sendHtml(MyRequest request, MyResponse response) {
        File file = new File(MyHTTPConstans.HTML_ROOT, request.getUri());
        try {
            copyFileToOut(file,response.getOut());
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>页面异常<<<<<<<<<<<");
            System.out.println(e.getMessage());
            send404Page(response.getOut());
        }

    }

    public static void send404Page(OutputStream out) {
        File file = new File(MyHTTPConstans.HTML_ROOT, MyHTTPConstans.PAGE_404);
        try {
            copyFileToOut(file,out);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>404 ERROR<<<<<<<<<<<");
        }
    }

    public static void send500Page(OutputStream out) {
        File file = new File(MyHTTPConstans.HTML_ROOT, MyHTTPConstans.PAGE_500);
        try {
            copyFileToOut(file,out);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>500 ERROR<<<<<<<<<<<");
        }
    }

    public static void copyFileToOut(File file, OutputStream out) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {

            //创建字节数组缓冲区
            byte[] buff = new byte[BUFFER_SIZE];
            //把文件中的数据读入到buff中
            int len = fis.read(buff);
            while (len != -1) {
                //把buff中的数据写到out.txt文件中
                out.write(buff, 0, len);
                //从新读取输入流，此时已到达输入流的结尾
                len = fis.read(buff);
            }
        }

    }
}
