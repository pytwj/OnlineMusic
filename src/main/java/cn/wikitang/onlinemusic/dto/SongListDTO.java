package cn.wikitang.onlinemusic.dto;

import lombok.Data;

/**
 * @Package: cn.wikitang.onlinemusic.dto
 * @ClassName: SongListDTO
 * @Author: WikiTang
 * @Date: 2022/3/27 22:38
 * @Description:
 */
@Data
public class SongListDTO {

    private String id;

    private String title;

    private String pic;

    private String introduction;

    private String style;
}
