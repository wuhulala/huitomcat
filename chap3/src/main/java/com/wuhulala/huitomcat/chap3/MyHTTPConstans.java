package com.wuhulala.huitomcat.chap3;

import java.io.File;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/25
 */
public class MyHTTPConstans {
    //地址
    static final String HTTP_HOST = "127.0.0.1";
    //端口
    static final int HTTP_PORT = 8080;
    //停止服务器命令
    static final String SHUTDOWN_COMMAND = "/shutdown";
    //html文件目录
    static final String HTML_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    //servlet类目录
    static final String SERVLET_ROOT = System.getProperty("user.dir") + File.separator + "app" + File.separator + "WEB-INF" + File.separator + "classes";

   //------------------页面位置-----------------------

    //默认欢迎页面
    static final String WELCOME_PAGE = "hello.html";
    //404 page
    static final String PAGE_404 = "404error.html";
    //500 page
    static final String PAGE_500 = "500error.html";
}
