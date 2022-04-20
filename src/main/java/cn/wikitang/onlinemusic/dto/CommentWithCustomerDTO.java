package cn.wikitang.onlinemusic.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: CommentWithCustomerDTO
 * @Author: WikiTang
 * @Date: 2022/4/20 19:57
 * @Description:
 */
@Data
public class CommentWithCustomerDTO {

    private Integer id;

    private Integer userId;

    private Integer songId;

    private Integer songListId;

    private String content;

    private Date createTime;

    private Integer type;

    private Integer up;

    private String avator;

    private String username;
}
