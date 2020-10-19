package Jwt;

import User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JwtToken {

    public static String createToken(String username,String password){
        long now = System.currentTimeMillis();//当前时间
        long exp = now + 1000*600;//过期时间为1分钟
        JwtBuilder builder= Jwts.builder().setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,password)
                .setExpiration(new Date(exp));//设置过期时间

        return builder.compact();
    }

    public static Claims parseJWT(String token, String password) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = password;

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public static Boolean isVerify(String token) {
        //签名秘钥，和生成的签名的秘钥一模一样
        System.out.println("This is the token");
        System.out.println(token);
        String[] theToken = token.split("[+]");

        User user = new User();
        System.out.println();
        token = theToken[0];
        String username = theToken[1];
        System.out.println(token);
        System.out.println(username);
        String password = user.getPassword(username);

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(password)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        System.out.println(claims);
        //System.out.println(claims.get("password"));
        if (claims.getSubject().equals(username)) {
            return true;
        }
        return false;
    }

}
