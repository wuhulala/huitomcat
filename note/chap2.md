##返回静态内容

>没有测试性能 估计也不太好 

```java
 public void sendHtml() {
        File file = new File(MyHttpServer.HTML_ROOT, request.getUri());
        try {
            copyFileToOut(file);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>页面异常<<<<<<<<<<<");
            System.out.println(e.getMessage());
            send404Page();
        }

    }

    public void send404Page() {
        File file = new File(MyHttpServer.HTML_ROOT, MyHttpServer.PAGE_404);
        try {
            copyFileToOut(file);
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>404 ERROR<<<<<<<<<<<");
        }
    }

    public void copyFileToOut(File file) throws IOException {
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
```

##返回动态内容

1. 扫描包
2. 读取类
3. 实例化类
4. 调用service方法

```java
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
```