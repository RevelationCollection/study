package com.study.spring.gateway.constant;

public interface GatewayExceptionCode {

	public int SYSTEM_EXCEPTION = 999;//系统级别错误
	public int HEADER_CHECK_ERROR = 998;//Header解析错误
	public int TOKEN_CHECK_ERROR = 997;//token失效
	public int PARAM_CHECK_ERROR = 996;//业务参数错误
	public int BLACK_URL_ERROR = 995;//黑名单地址错误
}
