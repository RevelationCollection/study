package com.study.spring.gateway.constant;

public interface GatewayConstant {

    // 用于存放token换回的用户信息 
    String TOKEN_USER_INFO_KEY = "TOKEN_USER_INFO_KEY";

    // 用户唯一标识存放在请求头中
    String IDENTIFY_HEADER_KEY = "identification";

    // 设置到 exchange.getAttributes()中的key 用于存放请求body
    String CACHED_REQUEST_BODY_OBJECT_KEY = "CACHED_REQUEST_BODY_OBJECT_KEY";

    // 设置到 exchange.getAttributes()中的key 用于存放是否对返回参数加密的标志
    String ENCRYPT_FLAG_KEY = "ENCRYPT_FLAG";

    // 返回参数 加密
    String REQ_RES_ENCRYPT = "1";

    // 返回参数 不加密
    String REQ_RES_NALMORE = "0";

    // 获取及刷新token请求地址
    String AUTH_URL = "/oauth/token";
    // 获取AppVersion请求地址
    String APP_VERSION_URL = "/res/update";

    // 请求的aeskey
    String REQ_AES_KEY = "req_aes_key";

    // 请求的msg
    String REQ_MSG = "req_msg";

    // 响应的aeskey
    String RES_AES_KEY = "res_aes_key";

    // 响应的msg
    String RES_MSG = "res_msg";
}
