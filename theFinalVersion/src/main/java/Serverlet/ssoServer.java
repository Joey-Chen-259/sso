package Serverlet;
import Jwt.JwtToken;
import User.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Date;
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
        //System.out.println(username);
        //System.out.println(password);



        if(password!=null && user.checkLogin(Integer.parseInt(username),password)){
            request.getSession().setAttribute("sign","yes");
            request.getSession().setAttribute("userName", username);
            request.getSession().setAttribute("password", password);
            //request.getSession().setAttribute("tokenSession",token);
            String token = JwtToken.createToken(username,password) + "+" + username;

            jedis.set("tokenId", token);
            jedis.expire("tokenId",1800);


            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(360);
            cookie.setPath("/");
            response.addCookie(cookie);
            //System.out.println("密码通过");

            request.getSession().setAttribute("isVerify","yes");
//               RequestDispatcher dispatcher = request.getRequestDispatcher("/Login");
//                dispatcher.forward(request, response);
            response.sendRedirect("/Login");

            return;

        }else{

            if(request.getSession().getAttribute("verifyToken")!=null && JwtToken.isVerify(request.getSession().getAttribute("verifyToken").toString())){
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

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

