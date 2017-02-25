package com.wuhulala.chap2;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/19
 */
class MyRequest implements ServletRequest{
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
                if("/".equals(uri)){
                    uri = MyHTTPConstans .WELCOME_PAGE;
                }
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

    //-------------------------------implements method ------------------------
    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
