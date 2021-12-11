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
@TableName(value = "song")
@ApiModel(value="Song对象", description="")
public class Song  {


    @TableId(value = "id")
    private Integer id;

    @TableField(value = "singer_id")
    private Integer singerId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "introduction")
    private String introduction;

    @ApiModelProperty(value = "发行时间")
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "pic")
    private String pic;

    @TableField(value = "lyric")
    private String lyric;

    @TableField(value = "url")
    private String url;


}
