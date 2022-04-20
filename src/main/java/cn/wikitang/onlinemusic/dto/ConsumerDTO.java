package cn.wikitang.onlinemusic.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

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

    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,16}$",message = "4到16位（字母，数字，下划线，减号）")
    private String username;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$",message = "6-20位字符和数字组合")
    private String password;

    private String sex;

    @Pattern(regexp = "^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$",message = "请输入11位有效手机号号码")
    private String phoneNum;

    private String email;

    private String birth;

    private String introduction;

    private String location;

    private String avator;
}
