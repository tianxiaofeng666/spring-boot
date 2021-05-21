package com.example.springbootmybatis.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author txf
 * @since 2021-05-21
 */
@Data
@TableName("t_user")
@ApiModel(value="TUser对象", description="用户信息表")
public class TUser {

     /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
     /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
     /**
     * 部门id
     */
    @TableField("depart_id")
    private Integer departId;
     /**
     * 密码
     */
    @TableField("password")
    private String password;
     /**
     * 年龄
     */
    @TableField("age")
    private Integer age;
     /**
     * 性别
     */
    @TableField("gender")
    private String gender;
     /**
     * 城市
     */
    @TableField("city")
    private String city;
     /**
     * 是否删除
     */
    @TableField("is_delete")
    private String isDelete;
     /**
     * 创建人
     */
    @TableField("creator")
    private String creator;
     /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
     /**
     * 修改人
     */
    @TableField("modifier")
    private String modifier;
     /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

}
