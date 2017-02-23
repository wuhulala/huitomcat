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