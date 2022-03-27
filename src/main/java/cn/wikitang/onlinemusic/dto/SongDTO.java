package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: SongDTO
 * @Author: WikiTang
 * @Date: 2022/3/27 10:36
 * @Description:
 */
@Data
public class SongDTO {

    private String id;

    private String singerId;

    private String name;

    private String introduction;

    private String createTime;

    private String updateTime;

    private String pic;

    private String lyric;

//    private String url;
}
