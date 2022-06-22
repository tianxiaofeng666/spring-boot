package com.example.demo.bean;

import com.example.demo.constant.RespConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础返回对象
 */
@Data
public class BaseRes {

	@ApiModelProperty(name = "code",value = "响应状态码")
    private int code;
    @ApiModelProperty(name = "msg",value = "提示消息")
	private String msg;
    @ApiModelProperty(name = "data",value = "响应数据")
	private Object data;

    /**
	* @Title: success 
	* @Description: 返回成功
	* @return RestfulResponse
	 */
	public static BaseRes success() {
		BaseRes res = new BaseRes();
		res.setCode(0);
		res.setMsg("请求调用成功");
		res.setData(null);
		return res;
	}
	public static BaseRes success(Object data) {
		BaseRes res = success();
		res.setData(data);
		return res;
	}
	public static BaseRes success(String msg) {
		BaseRes res = success();
		res.setMsg(msg);
		return res;
	}
	public static BaseRes success(String msg, Object data) {
		BaseRes res = success();
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	
	/**
	* @Title: error 
	* @Description: 返回错误
	* @return RestfulResponse
	 */
	public static BaseRes error() {
		BaseRes res = new BaseRes();
		res.setCode(RespConstant.ERROR);
		res.setMsg(RespConstant.ERROR_MSG);
		return res;
	}
	public static BaseRes error(String msg) {
		BaseRes res = new BaseRes();
		res.setCode(RespConstant.ERROR);
		res.setMsg(msg);
		return res;
	}
	public static BaseRes error(Object data) {
		BaseRes res = new BaseRes();
		res.setCode(RespConstant.ERROR);
		res.setMsg(RespConstant.ERROR_MSG);
		res.setData(data);
		return res;
	}
	public static BaseRes error(String msg, Object data) {
		BaseRes res = new BaseRes();
		res.setCode(RespConstant.ERROR);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	public static BaseRes error(int code, String msg) {
		BaseRes res = new BaseRes();
		res.setCode(code);
		res.setMsg(msg);
		return res;
	}
	public static BaseRes error(int code, String msg, Object data) {
		BaseRes res = new BaseRes();
		res.setCode(code);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}
	public static BaseRes error_404() {
		BaseRes res = new BaseRes();
		res.setCode(RespConstant.ERROR_404);
		res.setMsg(RespConstant.ERROR_404_MSG);
		return res;
	}
	public static BaseRes error_404(String msg) {
		BaseRes res = new BaseRes();
		res.setCode(RespConstant.ERROR_404);
		res.setMsg(msg);
		return res;
	}

}
