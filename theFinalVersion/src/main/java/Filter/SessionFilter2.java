package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;


/**
 * 判断用户是否登录,未登录则退出系统
 */
public class SessionFilter2 implements Filter {

    public FilterConfig config;

    public void destroy() {
        this.config = null;
    }
    String passUrl = "";

    public static boolean isContains(String container, String[] regx) {
        boolean result = false;

        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
//        String[] strArray = passUrl.split(";");
//
//        for (String str : strArray) {
//            if (str.equals(""))
//                continue;
//            if (httpRequest.getRequestURL().indexOf(str) >= 0) {
//                chain.doFilter(request, response);
//                return;
//            }
//        }
        if  (String.valueOf(session.getAttribute( "login2" )).equals("yes")) {
            chain.doFilter(request, response);
            return ;
        }
        //跳转至sso认证中心
        res.sendRedirect( "/app2/Login" );
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;

    }
}