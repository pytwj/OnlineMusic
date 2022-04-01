package cn.wikitang.onlinemusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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
@TableName(value = "consumer")
@ApiModel(value="Consumer对象", description="")
public class Consumer  {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "phone_num")
    private String phoneNum;

    @TableField(value = "email")
    private String email;

    @TableField(value = "birth")
    private Date birth;

    @TableField(value = "introduction")
    private String introduction;

    @TableField(value = "location")
    private String location;

    @TableField(value = "avator")
    private String avator;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;


}
