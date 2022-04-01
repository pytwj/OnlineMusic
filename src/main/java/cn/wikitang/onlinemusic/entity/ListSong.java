package cn.wikitang.onlinemusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author twj
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "list_song")
@ApiModel(value="ListSong对象", description="")
public class ListSong  {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "song_id")
    private Integer songId;

    @TableField(value = "song_list_id")
    private Integer songListId;


}
