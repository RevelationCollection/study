package com.study.spring.gateway.filter;

import com.study.spring.gateway.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -40;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("2.enter GlobalCacheRequestBodyFilter...");

        // 将 request body 中的内容 copy 一份，记录到 exchange 的一个自定义属性中
        Object cachedRequestBodyObject = exchange.getAttributeOrDefault(GatewayConstant.CACHED_REQUEST_BODY_OBJECT_KEY, null);
        // 如果已经缓存过，略过
        if (cachedRequestBodyObject != null) {
            return chain.filter(exchange);
        }
        // 如果没有缓存过，获取字节数组存入 exchange 的自定义属性中
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                }).defaultIfEmpty(new byte[0])
                .doOnNext(bytes -> exchange.getAttributes().put(GatewayConstant.CACHED_REQUEST_BODY_OBJECT_KEY, bytes))
                .then(chain.filter(exchange));
    }

}