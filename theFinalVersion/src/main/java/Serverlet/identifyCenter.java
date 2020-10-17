package Serverlet;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class identifyCenter extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if(request.getParameter("verifyToken") != null){
            Cookie cookie = new Cookie("test","test");
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            request.getSession().setAttribute("isLogin",true);
            request.getRequestDispatcher("/LoginDone").forward(request, response);
            return;
        }else{
            String message = String.format(
                    "对不起，用户名或密码有误！！请重新登录！2秒后为您自动跳到登录页面！！<meta http-equiv='refresh' content='2;url=%s'",
                    request.getContextPath()+"/servlet/LoginUIServlet");
            request.setAttribute("message",message);
            request.getRequestDispatcher("/Login").forward(request, response);
            return;
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

