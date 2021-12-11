package cn.wikitang.onlinemusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName(value = "comment")
@ApiModel(value="Comment对象", description="")
public class Comment  {


    @TableId(value = "id")
    private Integer id;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "song_id")
    private Integer songId;

    @TableField(value = "song_list_id")
    private Integer songListId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "up")
    private Integer up;


}
