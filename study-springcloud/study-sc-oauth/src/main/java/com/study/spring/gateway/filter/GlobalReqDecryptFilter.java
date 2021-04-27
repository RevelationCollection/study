package com.study.spring.gateway.filter;

import com.study.spring.gateway.config.RsaProperties;
import com.study.spring.gateway.constant.GatewayConstant;
import com.study.spring.gateway.constant.GatewayExceptionCode;
import com.study.spring.gateway.handle.GateWayException;
import com.study.spring.oauth.util.AesCoder;
import com.study.spring.oauth.util.JSONUtils;
import com.study.spring.oauth.util.RsaCoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GlobalReqDecryptFilter implements GlobalFilter, Ordered {

    @Autowired
    private RsaProperties rsaProperties;

    @Override
    public int getOrder() {
        return -30;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("3.enter GlobalReqDecryptFilter...");

        // 设置是否加密标识
        List<String> encryptFlagHeaders = exchange.getRequest().getHeaders().get("encrypt_flag");
        String encryptFlag = encryptFlagHeaders != null ? encryptFlagHeaders.get(0) : GatewayConstant.REQ_RES_ENCRYPT;
        exchange.getAttributes().put(GatewayConstant.ENCRYPT_FLAG_KEY, encryptFlag);

        ServerHttpRequest oldRequest = exchange.getRequest();
        String method = oldRequest.getMethodValue();
        MediaType mediaType = oldRequest.getHeaders().getContentType();
        URI uri = oldRequest.getURI();
        String decryptData = null;
        try {

            if ("POST".equals(method)) {

                Object cachedRequestBodyObject = exchange.getAttributeOrDefault(GatewayConstant.CACHED_REQUEST_BODY_OBJECT_KEY, null);
                String requestBody = new String((byte[]) cachedRequestBodyObject); // 客户端传过来的数据
                log.info("3.1 GlobalReqDecryptFilter post method data: {}", requestBody);
                //if (!StringUtils.isEmpty(requestBody)) {
                    if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
                        // form请求参数转换
                        Map<String, String> formBodyMap = transFormParamToMap(requestBody);
                        String aesKey = formBodyMap.get(GatewayConstant.REQ_AES_KEY);
                        String msg = formBodyMap.get(GatewayConstant.REQ_MSG);
                        // 参数校验
                        this.validateParams(aesKey,msg);
                        // 解密
                        aesKey = RsaCoder.decryptByPrivateKey(aesKey, rsaProperties.getPrivateKey());
                        msg = AesCoder.decryptData(aesKey, msg);
                        // 真实请求参数转换
                        Map<String, String> newBodyMap = JSONUtils.parseObject(msg, Map.class);
                        decryptData = transFormParamToString(newBodyMap);
                    } else if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || MediaType.APPLICATION_JSON_UTF8.isCompatibleWith(mediaType)) {
                        // 参数获取
//                        JSONObject jsonBody = JSON.parseObject(requestBody);
//                        String aesKey = jsonBody.getString(FilterConstant.REQ_AES_KEY);
//                        String msg = jsonBody.getString(FilterConstant.REQ_MSG);
//                        // 参数校验
//                        this.validateParams(aesKey,msg);
//                        // 解密
//                        aesKey = RsaCoder.decryptByPrivateKey(aesKey, rsaProperties.getPrivateKey());
//                        decryptData = AesCoder.decryptData(aesKey, msg);
                    } else {
                        throw new GateWayException(GatewayExceptionCode.HEADER_CHECK_ERROR,"不支持的媒体类型,content-type=" + mediaType);
                    }
                    log.info("3.2 GlobalReqDecryptFilter post method raw data: {}", decryptData);

                    ServerHttpRequest newRequest = this.assembleNewRequest(exchange,decryptData);

                    return chain.filter(exchange.mutate().request(newRequest).build());
                //}
            } else if ("GET".equals(method)) {

                log.info("3.1 GlobalReqDecryptFilter get method data: {}", oldRequest.getQueryParams());
                // 获取参数
                String aesKey = oldRequest.getQueryParams().get(GatewayConstant.REQ_AES_KEY).get(0);
                String msg = oldRequest.getQueryParams().get(GatewayConstant.REQ_MSG).get(0);
                // 参数校验
                this.validateParams(aesKey, msg);
                // 解密
                aesKey = RsaCoder.decryptByPrivateKey(aesKey, rsaProperties.getPrivateKey());
                msg = AesCoder.decryptData(aesKey, msg);

                Map<String, String> newBodyMap = JSONUtils.parseObject(msg, Map.class);
                decryptData = transFormParamToString(newBodyMap);

                log.info("3.2 GlobalReqDecryptFilter get method raw data: {}", decryptData);

                URI newUri = UriComponentsBuilder.fromUri(uri)
                        .replaceQuery(decryptData)
                        .build(false)
                        .toUri();
                ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
                return chain.filter(exchange.mutate().request(newRequest).build());

            } else {
                throw new GateWayException(GatewayExceptionCode.HEADER_CHECK_ERROR,"不支持的请求类型,method=" + method);
            }

        } catch (Exception e) {
            log.error("网关数据解析异常:{}", e.getMessage());
            throw new GateWayException(GatewayExceptionCode.SYSTEM_EXCEPTION,"网关数据解析异常");
        }
//        return chain.filter(exchange);
    }

    /**
     * form表单参数换成map
     * @param body
     * @return
     */
    private Map<String, String> transFormParamToMap(String body) {
        return Arrays.stream(body.split("&")).map(s -> s.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    /**
     * 转换成form表单参数
     * @param map
     * @return
     */
    private String transFormParamToString(Map<String, String> map) {
        return map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }

    /**
     * 参数校验
     *
     * @param reqResKey
     * @param reqMsg
     */
    private void validateParams(String reqResKey, String reqMsg) {
        if (StringUtils.isEmpty(reqResKey)) {
            throw new GateWayException(GatewayExceptionCode.PARAM_CHECK_ERROR,"缺少req_aes_key参数");
        }
        if (StringUtils.isEmpty(reqMsg)) {
            throw new GateWayException(GatewayExceptionCode.PARAM_CHECK_ERROR,"缺少req_msg参数");
        }

    }

    /**
     * post请求组装新的请求
     * @param exchange
     * @param requestBody
     * @return
     */
    private ServerHttpRequest assembleNewRequest(ServerWebExchange exchange,String requestBody){

        byte[] decryptBytes = requestBody.getBytes();
        // 根据解密后的参数重新构建请求 1.设置请求体 2.设置请求头
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        Flux<DataBuffer> bodyFlux = Flux.just(dataBufferFactory.wrap(decryptBytes));
        //ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(uri).build();
        ServerHttpRequest newRequest = exchange.getRequest().mutate().build();
        newRequest = new ServerHttpRequestDecorator(newRequest) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };

        // 构建新的请求头
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
        int length = decryptBytes.length;
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        headers.setContentLength(length);
        newRequest = new ServerHttpRequestDecorator(newRequest) {
            @Override
            public HttpHeaders getHeaders() {
                return headers;
            }
        };
        return newRequest;
    }

}
