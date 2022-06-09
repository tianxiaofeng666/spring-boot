package com.example.springbootswagger.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shs-cyhlwzytxf
 */
@Data
@ApiModel(value = "新增用户返回对象")
public class UserResp {
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "编号")
    private String no;
}
