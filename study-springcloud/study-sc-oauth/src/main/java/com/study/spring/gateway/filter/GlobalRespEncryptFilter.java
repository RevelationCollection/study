package com.study.spring.gateway.filter;

import com.study.spring.gateway.config.RsaProperties;
import com.study.spring.gateway.constant.GatewayConstant;
import com.study.spring.gateway.constant.GatewayExceptionCode;
import com.study.spring.gateway.handle.GateWayException;
import com.study.spring.oauth.util.AesCoder;
import com.study.spring.oauth.util.RsaCoder;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

@Slf4j
@Component
public class GlobalRespEncryptFilter implements GlobalFilter, Ordered {

    @Autowired
    private RsaProperties rsaProperties;

    @Override
    public int getOrder() {
        return -20;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("4.enter GlobalRespEncryptFilter...");
        String encryptFlag = exchange.getAttributeOrDefault(GatewayConstant.ENCRYPT_FLAG_KEY, GatewayConstant.REQ_RES_ENCRYPT);
        if (!GatewayConstant.REQ_RES_ENCRYPT.equals(encryptFlag)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffer -> {

                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffer);

                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        //释放掉内存
                        DataBufferUtils.release(join);
                        // 正常返回的数据
                        String rootData = new String(content, Charset.forName("UTF-8"));

                        log.info("4.1 GlobalRespEncryptFilter received data: {}",rootData);
                        byte[] respData = rootData.getBytes();
                        // 对数据进行加密
                        try {
                            String randomKey = AesCoder.genKeyAES();
                            String encryptData = AesCoder.encryptData(randomKey, rootData);
                            String encryptRandomKey = RsaCoder.encryptByPrivateKey(randomKey, rsaProperties.getPrivateKey());
                            JSONObject json = new JSONObject();
                            json.put(GatewayConstant.RES_AES_KEY, encryptRandomKey);
                            json.put(GatewayConstant.RES_MSG, encryptData);
                            respData = json.toString().getBytes();
                        } catch (Exception e) {
                            log.error("网关数据加密异常:{}", e.getMessage());
                            throw new GateWayException(GatewayExceptionCode.SYSTEM_EXCEPTION,"网关数据加密异常");
                        }
                        // 加密后的数据返回给客户端
                        byte[] uppedContent = new String(respData, Charset.forName("UTF-8")).getBytes();
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }
        };
        log.info("4.2 GlobalRespEncryptFilter outer data success");
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

}