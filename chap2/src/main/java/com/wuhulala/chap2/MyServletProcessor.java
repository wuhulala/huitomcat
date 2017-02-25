package com.wuhulala.chap2;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Servlet 处理器
 *
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/25
 */
public class MyServletProcessor {

    public void process(MyRequest request, MyResponse response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf('/')+1);
        URLClassLoader loader = null;


        try {
            URL[] urls = new URL[1];

            URLStreamHandler streamHandler = null;
            File classPath = new File(MyHTTPConstans.SERVLET_ROOT);

            //生成仓库地址
            String repository = (new URL("file",null,classPath.getCanonicalPath()+ File.separator)).toString();
            System.out.println(">>>>>>>>>仓库地址["+repository+"]");

            //因为重载同样是三个参数 如果使用null的话编译器是识别不出来是哪一个构造函数
            urls[0] = new URL(null,repository,streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>>>>加载仓库的时候出错了<<<<<<<<<<<<<<<");
            System.out.println(e.getMessage());
            MyStaticProcessor.send404Page(response.getOut());
        }

        //装载Class
        Class myClass = null;
        try {
            if (loader != null) {
                myClass = loader.loadClass(servletName);
            }
        } catch (ClassNotFoundException e) {
            System.out.println(">>>>>>>>>>>>>>>装载类的时候出错了:没有发现对应的类["+servletName+"]<<<<<<<<<<<<<<<");
            System.out.println(e.getMessage());
            MyStaticProcessor.send404Page(response.getOut());
        }

        Servlet servlet ;
        try {
            if (myClass != null) {
                servlet = (Servlet) myClass.newInstance();
                servlet.service(request,response);
            }
        } catch (Exception e) {
            System.out.println(">>>>>>>>>>>>>>>class cast to servlet 的时候出错了<<<<<<<<<<<<<<<");
            System.out.println(e.getMessage());
            MyStaticProcessor.send500Page(response.getOut());
        }

    }
}
