package cn.wikitang.onlinemusic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: LoginDTO
 * @Author: WikiTang
 * @Date: 2022/3/13 18:04
 * @Description:
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空，请核查！")
    private String name;

    @NotBlank(message = "密码不能为空，请核查！")
    private String password;
}
