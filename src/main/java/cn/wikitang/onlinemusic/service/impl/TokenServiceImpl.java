package cn.wikitang.onlinemusic.service.impl;

import cn.wikitang.onlinemusic.entity.Admin;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Package: cn.wikitang.onlinemusic.service.impl
 * @ClassName: TokenServiceImpl
 * @Author: WikiTang
 * @Date: 2022/3/20 20:23
 * @Description: 生成Token
 */
@Service
public class TokenServiceImpl {

    public String getToken(Admin admin) {
        Date start = new Date();
//        一小时有效时间
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(admin.getId().toString()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(admin.getPassword()));
        return token;
    }
}
