package com.example.plus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.plus.bean.PageQueryModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TUserQueryModel extends PageQueryModel {

    @ApiModelProperty(value = "主键", name = "id")
    private Long id;

    @ApiModelProperty(value = "用户名", name = "user_name")
    private String userName;

    @ApiModelProperty(value = "部门id", name = "depart_id")
    private Integer departId;

    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    @ApiModelProperty(value = "年龄", name = "age")
    private Integer age;

    @ApiModelProperty(value = "性别", name = "gender")
    private String gender;

    @ApiModelProperty(value = "城市", name = "city")
    private String city;

    @ApiModelProperty(value = "", name = "is_delete")
    private String isDelete;

    @ApiModelProperty(value = "", name = "creator")
    private String creator;

    @ApiModelProperty(value = "创建时间开始日期时间", name = "create_time", notes = "TimeFrom")
    private LocalDateTime createTimeFrom;

    @ApiModelProperty(value = "创建时间结束日期时间", name = "create_time", notes = "TimeTo")
    private LocalDateTime createTimeTo;

    @ApiModelProperty(value = "", name = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "", name = "modifier")
    private String modifier;

    @ApiModelProperty(value = "更新时间开始日期时间", name = "update_time", notes = "TimeFrom")
    private LocalDateTime updateTimeFrom;

    @ApiModelProperty(value = "更新时间结束日期时间", name = "update_time", notes = "TimeTo")
    private LocalDateTime updateTimeTo;

    @ApiModelProperty(value = "", name = "update_time")
    private LocalDateTime updateTime;
}
