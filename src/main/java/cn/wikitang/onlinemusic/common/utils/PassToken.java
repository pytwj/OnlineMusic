package cn.wikitang.onlinemusic.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package: cn.wikitang.onlinemusic.common.utils
 * @ClassName: PassToken
 * @Author: WikiTang
 * @Date: 2022/3/20 20:46
 * @Description: 用来跳过验证的PassToken
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
