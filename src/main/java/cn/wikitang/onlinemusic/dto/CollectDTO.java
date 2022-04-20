package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: CollectDTO
 * @Author: WikiTang
 * @Date: 2022/4/11 10:08
 * @Description:
 */
@Data
public class CollectDTO {

    private String id;

    private String userId;

    private String type;

    private String songId;

    private String songListId;
}
