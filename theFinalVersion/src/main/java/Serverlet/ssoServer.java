package Serverlet;
import Jwt.JwtToken;
import User.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
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

    public JedisPoolConfig config = new JedisPoolConfig();
    public JedisPool jedisPool = new JedisPool(config,"localhost",6379);
    //public JedisPool jedisPool2 = new JedisPool(config,"localhost",6380);
    public Jedis jedis = jedisPool.getResource();
    Jedis jedisUrl = jedisPool.getResource();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取用户填写的登录用户名
        User user = new User();

        String username = request.getParameter("username");
        //获取用户填写的登录密码
        String password = request.getParameter("password");

        //System.out.println(username);
        //System.out.println(password);

        if(request.getSession().getAttribute("logoutSign")!=null&&request.getSession().getAttribute("logoutSign").equals("yes")){
            request.getSession().setAttribute("logoutSign",null);
            System.out.println("---这里已经发送了登出信息---");
            Set<String> s = jedisUrl.keys("*");
            Iterator<String> it = s.iterator();
            while (it.hasNext()) {

                //System.out.println("这里是分界线");
                String key = (String) it.next();
                Cookie cookie2 = new Cookie("login","no");
                cookie2.setMaxAge(60);
                cookie2.setPath("/" + key);
                response.addCookie(cookie2);

                Cookie cookie3 = new Cookie("token",null);
                cookie3.setPath("/" + key);
                response.addCookie(cookie3);
            }

            response.sendRedirect("/app1/Login");

            return;
        }

        if(jedis.exists("tokenId") && request.getSession().getAttribute("verifyToken")==null){

            System.out.println("---已经检测到了token---");
            Cookie cookie = new Cookie("token",jedis.get("tokenId"));
            cookie.setMaxAge(360);
            cookie.setPath("/" + request.getSession().getAttribute("theUrl").toString());
            response.addCookie(cookie);
            request.getSession().setAttribute("isVerify","yes");

            response.sendRedirect("/" + request.getSession().getAttribute("theUrl").toString() + "/Login");

        }else{
            if((!jedis.exists("tokenId") &&request.getSession().getAttribute("theJps")!=null &&request.getSession().getAttribute("theUrl")!=null&&request.getSession().getAttribute("theJps")!="") || (request.getSession().getAttribute("signForExpire")!=null&&request.getSession().getAttribute("signForExpire").equals("yes"))){
                System.out.println("---这里是初始状态跳转页面---");
                //String url = request.getSession().getAttribute("theJps").toString();
                request.getSession().setAttribute("theJps","");

                Cookie cookie = new Cookie("theUrl",request.getSession().getAttribute("theUrl").toString());
                cookie.setMaxAge(360);
                cookie.setPath("/" + request.getSession().getAttribute("theUrl").toString());
                response.addCookie(cookie);

                request.getSession().setAttribute("signForExpire",null);

                System.out.println("准备进去页面");
                request.getRequestDispatcher("/WEB-INF/pages/loginPage.jsp").forward(request, response);
            }else{
                if(password!=null && user.checkLogin(Integer.parseInt(username),password)){
                    System.out.println("---这里是密码通过验证，设置token并返回---");
                    request.getSession().setAttribute("sign","yes");
                    request.getSession().setAttribute("userName", username);
                    request.getSession().setAttribute("password", password);
                    //request.getSession().setAttribute("tokenSession",token);
                    String token = JwtToken.createToken(username,password) + "+" + username;

                    jedis.set("tokenId", token);
                    jedis.expire("tokenId",1800);

                    Cookie cookie = new Cookie("token",token);
                    cookie.setMaxAge(360);
                    cookie.setPath("/" + request.getSession().getAttribute("theUrl").toString());
                    response.addCookie(cookie);
                    //System.out.println("密码通过");

                    request.getSession().setAttribute("isVerify","yes");
//               RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
//                dispatcher.forward(request, response);
                    response.sendRedirect("/" + request.getSession().getAttribute("theUrl").toString() + "/Login");
                    return;

                }else{

                    if(request.getSession().getAttribute("verifyToken")!=null && JwtToken.isVerify(request.getSession().getAttribute("verifyToken").toString())){
                        System.out.println("---这里是token已经验证成功，并且注册系统---");
                        request.getSession().setAttribute("pass","yes");
                        request.getSession().setAttribute("sign","yes");
                        System.out.println("这里是添加Url操作----");
                        System.out.println(request.getSession().getAttribute("theUrl"));
                        System.out.println("这里是添加Url操作----");
                        jedisUrl.set(request.getSession().getAttribute("theUrl").toString(),request.getSession().getAttribute("theUrl").toString());
                        request.getSession().setAttribute("verifyToken",null);
                        response.sendRedirect("/" + request.getSession().getAttribute("theUrl").toString() + "/Login");

                        return;
                    }else{
                        String message = String.format(
                                "对不起，用户名或密码有误！！请重新登录！2秒后为您自动跳到登录页面！！<meta http-equiv='refresh' content='2;url=%s'",
                                request.getContextPath()+"/servlet/LoginUIServlet");
                        request.setAttribute("message",message);
                        request.getSession().setAttribute("sign","no");
                        response.sendRedirect("/" + request.getSession().getAttribute("theUrl").toString() + "/Login");
                        return;
                    }
                }
            }
        }





    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

