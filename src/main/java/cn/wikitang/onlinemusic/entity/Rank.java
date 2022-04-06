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
@TableName(value = "rank_info")
@ApiModel(value="Rank对象", description="")
public class Rank  {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "songListId")
    private Long songListId;

    @TableField(value = "consumerId")
    private Long consumerId;

    @TableField(value = "score")
    private Integer score;


}
