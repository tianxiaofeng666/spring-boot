package com.example.demo.exception;

/**
 * 业务异常类
 * @author shs-cyhlwzytxf
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private String msg;
	private int code;

	public BusinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BusinessException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BusinessException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public BusinessException(ErrorMessageEnum processError) {
		super(processError.getErrMsg());
		this.msg = processError.getErrMsg();
		this.code = processError.getErrCode();
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
