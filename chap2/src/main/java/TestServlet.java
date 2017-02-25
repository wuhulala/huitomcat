

import javax.servlet.*;
import java.io.IOException;

/**
 * @author xueaohui
 * @version 1.0
 * @date 2017/2/25
 */
public class TestServlet implements Servlet{

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("TestServlet is loaded");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        String remoteAddr = req.getRemoteAddr();
        String host = req.getRemoteHost();

        String message  = "您的地址为:["+remoteAddr+":"+host+"]";

        resp.getWriter().println(message);
        resp.getWriter().println("您的协议为:[ "+req.getProtocol()+"]");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
