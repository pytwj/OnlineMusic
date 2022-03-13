package cn.wikitang.onlinemusic.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @Package: cn.wikitang.onlinemusic.common.utils
 * @ClassName: UUIDUtil
 * @Author: WikiTang
 * @Date: 2022/3/13 22:42
 * @Description: uuid生成工具类
 */
public class UUIDUtil {
    private UUIDUtil() {
    }

    public static String getuuid(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    public static String getuuid(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    /***
     * 流程设置UUID
     * @return
     */
    public static String getuuid(String prefix) {
        int length = 32;
        int radix = 16;//16进制
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < length; i++) {
            int start = 0;
            Double fb = Math.random() * radix;
            int result = fb.intValue();
            sb.append(chars[start | result]);
        }
        return sb.toString();
    }

    public static String getLetter(int num) {
        StringBuffer str = new StringBuffer();
        for(int i=0;i<6;i++){
            str.append((char) (new Random().nextInt(26) + 65));
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println(getLetter(6));
    }
}

