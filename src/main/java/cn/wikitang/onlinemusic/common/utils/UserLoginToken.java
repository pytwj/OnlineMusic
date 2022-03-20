package cn.wikitang.onlinemusic.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package: cn.wikitang.onlinemusic.common.utils
 * @ClassName: UserLoginToken
 * @Author: WikiTang
 * @Date: 2022/3/20 20:52
 * @Description: 需要登录才能进行操作的注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserLoginToken {
    boolean required() default true;
}
