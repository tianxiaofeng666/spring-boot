package com.example.seataclouduser.common;

public enum SeataEnum {
    CLOUD_USER_ERROR(1,"云服创建用户失败！"),
    USER_CENTER_ERROR(2,"同步用户中台失败！");

    private int code;
    private String errorMsg;

    SeataEnum(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
