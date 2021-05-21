package com.example.springbootmybatis.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author txf
 * @since 2021-05-21
 */
@Data
@TableName("t_depart")
@ApiModel(value="TDepart对象", description="部门表")
public class TDepart {

     /**
     * 部门id
     */
    @TableField("id")
    private Integer id;
     /**
     * 部门名称
     */
    @TableField("name")
    private String name;
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
