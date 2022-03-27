package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: SingerDTO
 * @Author: WikiTang
 * @Date: 2022/3/23 21:01
 * @Description:
 */
@Data
public class SingerDTO {

    private String id;

    private String name;

    private String sex;

    private String pic;

    private String birth;

    private String location;

    private String introduction;
}
