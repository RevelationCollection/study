package com.study.spring.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.Random;

public class CustomFilterFactory extends AbstractGatewayFilterFactory<CustomFilterFactory> {

    private Logger log = LoggerFactory.getLogger(getClass());

    public CustomFilterFactory() {
        super();
    }

    @Override
    public GatewayFilter apply(CustomFilterFactory config) {
        return ((exchange, chain) -> {
            log.info("  start CustomFilterFactory ");
            if (new Random().nextInt(20)%2==0) {
                //继续传播
                return chain.filter(exchange);
            }
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders httpHeader = response.getHeaders();
            httpHeader.add("Content-Type","application/json;charset=UTF-8");
            String warningStr = "拒绝访问";
            DataBuffer dataBuffer = response.bufferFactory().wrap(warningStr.getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        });
    }
}
