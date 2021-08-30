package com.example.springbootswagger.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author shs-cyhlwzytxf
 */
@Data
public class RoleReq {

    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String comment;
}
