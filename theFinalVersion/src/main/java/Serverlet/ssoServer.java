package Serverlet;
import User.User;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.UUID;

import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ssoServer extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取用户填写的登录用户名
        User user = new User();
        String username = request.getParameter("username");
        //获取用户填写的登录密码
        String password = request.getParameter("password");
        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config,"localhost",6379);
        Jedis jedis = jedisPool.getResource();
        System.out.println(username);
        System.out.println(password);



        if(password!=null && user.checkLogin(Integer.parseInt(username),password)){
            request.getSession().setAttribute("sign","yes");
            request.getSession().setAttribute("userName", username);
            request.getSession().setAttribute("password", password);
            String token = username + password;
            request.getSession().setAttribute("tokenSession",token);
            jedis.set("tokenId", username+password);
            jedis.expire("tokenId",1800);

            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(360);
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("密码通过");
            request.getSession().setAttribute("isVerify","yes");
//               RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
//                dispatcher.forward(request, response);
            response.sendRedirect("/Login");

            return;

        }else{
            if(request.getSession().getAttribute("verifyToken")!=null && request.getSession().getAttribute("verifyToken").equals(jedis.get("tokenId"))){
                request.getSession().setAttribute("pass","yes");
                request.getSession().setAttribute("sign","yes");
                response.sendRedirect("/Login");
                return;
            }else{
                String message = String.format(
                        "对不起，用户名或密码有误！！请重新登录！2秒后为您自动跳到登录页面！！<meta http-equiv='refresh' content='2;url=%s'",
                        request.getContextPath()+"/servlet/LoginUIServlet");
                request.setAttribute("message",message);
                request.getSession().setAttribute("sign","no");
                response.sendRedirect("/Login");
                return;
            }
        }





//        //登录成功后，就将用户存储到session中
//        request.getSession().setAttribute("user", user);
//        String message = String.format(
//                "恭喜：%s,登陆成功！本页将在3秒后跳到首页！！<meta http-equiv='refresh' content='3;url=%s'",
//                user.getUserName(),
//                request.getContextPath()+"/index.jsp");
//        request.setAttribute("message",message);
//        request.getRequestDispatcher("/message.jsp").forward(request, response);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
//        //使用request对象的getSession()获取session，如果session不存在则创建一个
//        HttpSession session = request.getSession();
//        //将数据存储到session中
//        session.setAttribute("data", "孤傲苍狼");
//        //获取session的Id
//        String sessionId = session.getId();
//        //判断session是不是新创建的
//        if (session.isNew()) {
//            response.getWriter().print("session创建成功，session的id是："+sessionId);
//        }else {
//            response.getWriter().print("服务器已经存在该session了，session的id是："+sessionId);
//        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

