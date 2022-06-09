/** 以下代码由代码生成器自动生成，如无必要，请勿修改！2021-6-7 9:32:59 **/
package com.example.springbootswagger.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generator
 * @date 2021-6-7 9:32:59
 */
@ApiModel
public class RestfulResponse<T> {
    @ApiModelProperty("code：0 成功")
    private int code;
    @ApiModelProperty("返回描述")
    private String message;
    @ApiModelProperty("返回数据")
    private T result;

    private RestfulResponse() {
    }

    private RestfulResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }


    public RestfulResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }



    /**
     * @param code
     * @param message
     * @param result
     * @return
     */
    public static <T> RestfulResponse createOne(int code,String message,T result) {
        return new RestfulResponse(code, message, result);
    }


    /**
     * @return
     */
    public static <T> RestfulResponse<T> success() {
        return new RestfulResponse(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param result 获取的数据
     */
    public static <T> RestfulResponse<T> success(T result) {
        return new RestfulResponse(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), result);
    }

    /**
     * 成功返回结果
     *
     * @param result  获取的数据
     * @param message 提示信息
     */
    public static <T> RestfulResponse<T> success(String message, T result) {
        return new RestfulResponse(ResultCode.SUCCESS.getCode(), message, result);
    }

    /**
     * 失败返回结果
     *
     */
    public static RestfulResponse failed() {
        return new RestfulResponse(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static RestfulResponse failed(String message) {
        return new RestfulResponse(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param code 编码
     * @param message 提示信息
     */
    public static RestfulResponse failed(int code,String message) {
        return new RestfulResponse(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
