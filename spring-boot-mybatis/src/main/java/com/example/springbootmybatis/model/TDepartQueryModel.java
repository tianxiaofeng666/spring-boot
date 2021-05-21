package com.example.springbootmybatis.model;

import com.example.springbootmybatis.bean.PageQueryModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TDepartQueryModel extends PageQueryModel {

    @ApiModelProperty(value = "部门id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "部门名称", name = "name")
    private String name;

    @ApiModelProperty(value = "是否删除", name = "is_delete")
    private String isDelete;

    @ApiModelProperty(value = "创建人", name = "creator")
    private String creator;

    @ApiModelProperty(value = "创建时间开始日期时间", name = "create_time", notes = "TimeFrom")
    private LocalDateTime createTimeFrom;

    @ApiModelProperty(value = "创建时间结束日期时间", name = "create_time", notes = "TimeTo")
    private LocalDateTime createTimeTo;

    @ApiModelProperty(value = "创建时间", name = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人", name = "modifier")
    private String modifier;

    @ApiModelProperty(value = "更新时间开始日期时间", name = "update_time", notes = "TimeFrom")
    private LocalDateTime updateTimeFrom;

    @ApiModelProperty(value = "更新时间结束日期时间", name = "update_time", notes = "TimeTo")
    private LocalDateTime updateTimeTo;

    @ApiModelProperty(value = "修改时间", name = "update_time")
    private LocalDateTime updateTime;
}
