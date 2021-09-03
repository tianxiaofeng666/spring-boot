package com.example.springbootswagger.exception;


import com.example.springbootswagger.bean.ResultCode;

/**
 * 自定义异常
 * @author shs-cyhlwzytxf
 */
public class BusinessException extends RuntimeException {

    private int code;
    private String msg;

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
