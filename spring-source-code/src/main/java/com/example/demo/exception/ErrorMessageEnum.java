package com.example.demo.exception;


import com.example.demo.constant.RespConstant;

/**
 * @author tanglj31
 */

public enum ErrorMessageEnum {
    //服务异常
    SERVICE_EXCEPTION(RespConstant.ERROR, "服务异常，请联系管理员"),
    /**
     * 定义错误枚举类
     */
    NO_PERSONAL_ARCHIVE_INFO(80005,"请先完善个人档案信息!");

    private static final String EMPTY = "";

    private int errCode;

    private String errMsg;

    /**
     * * Get message by code
     * * @param code
     *
     * @return name
     */
    public static String getText(int code) {
        for (ErrorMessageEnum em : ErrorMessageEnum.values()) {
            if (em.errCode == code) {
                return em.errMsg;
            }
        }
        return EMPTY;
    }

    ErrorMessageEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
