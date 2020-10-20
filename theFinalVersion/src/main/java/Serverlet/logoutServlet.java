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

public class logoutServlet extends HttpServlet{

    JedisPoolConfig config = new JedisPoolConfig();
    JedisPool jedisPool = new JedisPool(config,"localhost",6379);
    //public JedisPool jedisPool2 = new JedisPool(config,"localhost",6380);

    public Jedis jedis = jedisPool.getResource();
    public Jedis jedisUrl = jedisPool.getResource();
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取用户填写的登录用户名
        User user = new User();
//        Set<String> s = jedisUrl.keys("*");
//        Iterator<String> it = s.iterator();
//        while (it.hasNext()) {
//
//            //System.out.println("这里是分界线");
//
//            String key = (String) it.next();
//            if (key.equals("tokenId")) {
//                jedisUrl.del("tokenId");
//                System.out.println("---全局会话已经消失---");
//                request.getSession().setAttribute("logoutSign","yes");
//                response.sendRedirect("/ssoServer");
//                return;
//            }
//        }
        if(jedis.get("tokenId")!=null){
            jedisUrl.del("tokenId");
            System.out.println("---全局会话已经消失---");
            request.getSession().setAttribute("logoutSign","yes");
            response.sendRedirect("/ssoServer");
            return;
        }
        response.sendRedirect("/app1/Login");
        return;
        //request.getRequestDispatcher("/WEB-INF/pages/loginDone.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

