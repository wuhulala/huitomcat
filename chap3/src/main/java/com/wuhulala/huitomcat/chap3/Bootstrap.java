package com.wuhulala.huitomcat.chap3;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/3/1
 */
public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
