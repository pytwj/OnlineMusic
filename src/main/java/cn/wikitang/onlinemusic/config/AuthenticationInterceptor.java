package cn.wikitang.onlinemusic.config;

import cn.wikitang.onlinemusic.common.utils.PassToken;
import cn.wikitang.onlinemusic.common.utils.UserLoginToken;
import cn.wikitang.onlinemusic.entity.Admin;
import cn.wikitang.onlinemusic.service.IAdminService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Package: cn.wikitang.onlinemusic.config
 * @ClassName: AuthenticationInterceptor
 * @Author: WikiTang
 * @Date: 2022/3/20 20:38
 * @Description:
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private IAdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())){
            return true;
        }
//        从请求头获取token
        String token = request.getHeader("Authorization");
//        如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
//        检查是否有passtoken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
//        检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
//                执行认证
                if (token == null) {
                    response.setStatus(401);
                    throw new RuntimeException("无token，请重新登录");
                }
//                获取token的id
                String userId = null;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                int id = Integer.parseInt(userId);
                Admin admin = adminService.getById(id);
                if (admin == null){
                    throw new RuntimeException("用户不存在，请重新登录");
                }
//                验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    response.setStatus(401);
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
