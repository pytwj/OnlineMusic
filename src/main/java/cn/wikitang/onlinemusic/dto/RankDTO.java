package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: RankDTO
 * @Author: WikiTang
 * @Date: 2022/4/6 13:16
 * @Description:
 */
@Data
public class RankDTO {

    private String id;

    private String songListId;

    private String consumerId;

    private String score;
}
