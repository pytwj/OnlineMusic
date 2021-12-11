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
@TableName(value = "singer")
@ApiModel(value="Singer对象", description="")
public class Singer  {


    @TableId(value = "id")
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "pic")
    private String pic;

    @TableField(value = "birth")
    private Date birth;

    @TableField(value = "location")
    private String location;

    @TableField(value = "introduction")
    private String introduction;


}
