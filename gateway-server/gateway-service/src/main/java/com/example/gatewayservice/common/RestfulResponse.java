package com.example.gatewayservice.common;

/**
 *统一响应消息报文
 */
public class RestfulResponse {
    private int code;
    private String message;
    private Object result;

    private RestfulResponse() {
    }

    private RestfulResponse(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }


    /**
     * @param code
     * @param message
     * @param result
     * @return
     */
    public static RestfulResponse createOne(int code, String message, Object result) {
        return new RestfulResponse(code, message, result);
    }


    /**
     * @return
     */
    public static RestfulResponse success() {
        return new RestfulResponse(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }


    /**
     * 成功返回结果
     *
     * @param result 获取的数据
     */
    public static RestfulResponse success(Object result) {
        return new RestfulResponse(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), result);
    }

    /**
     * 成功返回结果
     *
     * @param result  获取的数据
     * @param message 提示信息
     */
    public static RestfulResponse success(String message, Object result) {
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
    public static RestfulResponse failed(int code, String message) {
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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
