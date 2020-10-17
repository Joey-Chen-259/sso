package Serverlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

//登陆页面 负责页面跳转
public class loginServerlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = "";
        String login = "";

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                token = cookie.getValue();
            }
            if(cookie.getName().equals("login")){
                login = cookie.getValue();
            }
        }


        if(request.getSession().getAttribute("login")!=null&&request.getSession().getAttribute("login").equals("yes")){
            response.sendRedirect("/LoginDone");
        }else{
            if(request.getSession().getAttribute("pass")!=null && request.getSession().getAttribute("pass").equals("yes")){
                request.getSession().setAttribute("login","yes");
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60);
                response.sendRedirect("/Login");
            }else{
                if((!token.equals("")) && !(request.getSession().getAttribute("sign")!=null && request.getSession().getAttribute("sign").equals("no"))){
                    Cookie cookie2 = new Cookie("test3","test3");
                    cookie2.setMaxAge(60);
                    cookie2.setPath("/");
                    response.addCookie(cookie2);

                    request.getSession().setAttribute("verifyToken",token);
                    response.sendRedirect("/ssoServer");

                }else{
                    request.getRequestDispatcher("/WEB-INF/pages/loginPage.jsp").forward(request, response);
                }
            }

        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
