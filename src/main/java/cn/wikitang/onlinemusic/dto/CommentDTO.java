package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: CommentDTO
 * @Author: WikiTang
 * @Date: 2022/4/10 15:50
 * @Description:
 */
@Data
public class CommentDTO {

    private String id;

    private String userId;

    private String songId;

    private String songListId;

    private String content;

    private String type;

    private String up;

}
