package Serverlet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

//登陆页面 负责页面跳转

public class loginServerlet extends HttpServlet {
    JedisPoolConfig config = new JedisPoolConfig();
    JedisPool jedisPool = new JedisPool(config,"localhost",6379);
    Jedis jedis3 = jedisPool.getResource();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = "";
        String login = "";
        request.getSession().setAttribute("theUrl","app1");
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                token = cookie.getValue();
            }
            if(cookie.getName().equals("login")){
                login = cookie.getValue();
            }

        }


        if(login.equals("yes")){
            System.out.println("---显示已经存在局部会话，不用验证token---");
            response.sendRedirect("/app1/LoginDone");
        }else{
            if(request.getSession().getAttribute("pass")!=null && request.getSession().getAttribute("pass").equals("yes")){
                request.getSession().setAttribute("login1","yes");
                System.out.println("---这里设置cookie（局部会话）---");

                Cookie cookie2 = new Cookie("login","yes");
                cookie2.setMaxAge(60);
                cookie2.setPath("/app1");
                response.addCookie(cookie2);
                request.getSession().setAttribute("pass",null);

                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60);
                response.sendRedirect("/app1/Login");
            }else{
                if((!token.equals("")) && !(request.getSession().getAttribute("sign")!=null && request.getSession().getAttribute("sign").equals("no"))){
                    System.out.println("---这里是去验证token---");
                    Cookie cookie2 = new Cookie("test3","test3");
                    cookie2.setMaxAge(60);
                    cookie2.setPath("/app1");
                    response.addCookie(cookie2);
                    //request.getSession().setAttribute("sign",null);
                    request.getSession().setAttribute("verifyToken",token + "+" + request.getRequestURL());
                    response.sendRedirect("/ssoServer");

                }else{
                    System.out.println("这里是初始状态");
                    request.getSession().setAttribute("theUrl","app1");
                    if((request.getSession().getAttribute("sign")!=null && request.getSession().getAttribute("sign").equals("no"))){
                        request.getSession().setAttribute("signForExpire","yes");
                        request.getSession().setAttribute("sign",null);
                    }
                    request.getSession().setAttribute("theJps",request.getRequestURL());
                    response.sendRedirect("/ssoServer");
                    //request.getRequestDispatcher("/WEB-INF/pages/loginPage.jsp").forward(request, response);
                }
            }

        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
