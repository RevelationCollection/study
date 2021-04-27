package com.study.spring.gateway.handle;

import com.baomidou.mybatisplus.extension.api.R;
import com.study.spring.gateway.config.RsaProperties;
import com.study.spring.gateway.constant.GatewayConstant;
import com.study.spring.oauth.util.AesCoder;
import com.study.spring.oauth.util.JSONUtils;
import com.study.spring.oauth.util.RsaCoder;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private RsaProperties rsaProperties;
    
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        
        String msg;
        int code = 0 ;
        if (ex instanceof NotFoundException) {
            msg = "服务未找到";
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            msg = responseStatusException.getMessage();
        } else if (ex instanceof GateWayException) {
            msg = ((GateWayException) ex).getMsg();
            code = ((GateWayException) ex).getCode();
        }else {
            msg = "内部服务器错误";
        }
        
        JSONObject json = new JSONObject();
		try {
			String randomKey = AesCoder.genKeyAES();
			String encryptData = AesCoder.encryptData(randomKey, JSONUtils.toJSONString( 0!=code ? R.failed(msg):R.failed(msg)));
			String encryptRandomKey = RsaCoder.encryptByPrivateKey(randomKey, rsaProperties.getPrivateKey());
			json.put(GatewayConstant.RES_AES_KEY, encryptRandomKey);
			json.put(GatewayConstant.RES_MSG, encryptData);
		} catch (Exception e) {
			log.error("[网关异常处理]返回加密信息错误,{}",  e.getMessage(),e);
		}
        
        final byte[] respData = json.toString().getBytes();
        
        log.error("[网关异常处理]请求路径:{},异常信息:{},{}", exchange.getRequest().getPath(), ex.getMessage(),ex);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(respData);
        }));
    }
}