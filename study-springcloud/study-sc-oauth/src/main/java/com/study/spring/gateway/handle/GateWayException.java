package com.study.spring.gateway.handle;

/**
 * 自定义异常
 **/
public class GateWayException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    
    public GateWayException(int codeInfo,String msgInfo) {
    	super();
    	this.code = codeInfo;
    	this.msg = msgInfo;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
    
}
