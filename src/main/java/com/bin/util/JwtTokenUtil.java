package com.bin.util;


import com.bin.pojo.User;
import io.jsonwebtoken.*;


import java.util.Date;

public class JwtTokenUtil {

    public static final String SUBJECT = "onehee";

    public static final long EXPIRE = 1000*60*60*24*7;  //过期时间，毫秒，一周

    //秘钥
    public static final  String APPSECRET = "chenbinjie";

    /**
     * 生成jwt
     * @param user
     * @return
     */
    public static String createJWT(User user){

        if(user == null || user.getId() == null || user.getUsername() == null
                || user.getPassword()==null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id",user.getId())
                .claim("username",user.getUsername())
                .claim("password",user.getPassword())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();

        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token ){

        try{
            final Claims claims =  Jwts.parser().setSigningKey(APPSECRET).
                    parseClaimsJws(token).getBody();
            return  claims;

        }catch (Exception e){ }
        return null;

    }
}
