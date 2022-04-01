package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: ConsumerDTO
 * @Author: WikiTang
 * @Date: 2022/4/1 12:04
 * @Description:
 */
@Data
public class ConsumerDTO {

    private String id;

    private String username;

    private String password;

    private String sex;

    private String phoneNum;

    private String email;

    private String birth;

    private String introduction;

    private String location;

    private String avator;
}
