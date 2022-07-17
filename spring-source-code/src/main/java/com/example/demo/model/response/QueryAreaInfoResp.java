package com.example.demo.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author RX
 */
@Data
@ApiModel(value = "查询省市区返回对象")
public class QueryAreaInfoResp {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "级别")
    private String level;

    @ApiModelProperty(value = "地区码")
    private String adcode;

    @ApiModelProperty(value = "父级地区码")
    private String parentAdcode;

    @ApiModelProperty(value = "父级地区码")
    private List<QueryAreaInfoResp> children;
}
