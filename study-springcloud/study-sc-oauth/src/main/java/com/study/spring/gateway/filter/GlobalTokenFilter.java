package com.study.spring.gateway.filter;

import com.baomidou.mybatisplus.extension.api.R;
import com.study.spring.gateway.constant.GatewayConstant;
import com.study.spring.gateway.constant.GatewayExceptionCode;
import com.study.spring.gateway.handle.GateWayException;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Component
public class GlobalTokenFilter implements GlobalFilter, Ordered {


    @Override
    public int getOrder() {
        return -99;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("1.enter GlobalTokenFilter...");

        ServerHttpRequest request = exchange.getRequest();
        // 登录请求，不做token校验处理
        if (request.getURI().getPath().endsWith("/oauth/token")) {
            return chain.filter(exchange);
        }

        String accessToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(accessToken)) {
            throw new GateWayException(GatewayExceptionCode.HEADER_CHECK_ERROR,"请求头中缺少Authorization参数");
        }

        R<JSONObject> userInfoRes = null ;//remoteCustService.getUserInfoByToken(accessToken);
        String username = null;
        if (userInfoRes.getCode() == 200 && !StringUtils.isEmpty(userInfoRes.getData())) {
//            username = userInfoRes.get("username");
            if (StringUtils.isEmpty(username)) {
                throw new GateWayException(GatewayExceptionCode.TOKEN_CHECK_ERROR,"token已过期或不合法");
            }
            exchange.getAttributes().put(GatewayConstant.TOKEN_USER_INFO_KEY, username);
        } else {
            throw new GateWayException(GatewayExceptionCode.TOKEN_CHECK_ERROR,"token已过期或不合法");
        }
        // 添加自定义的请求头
        final String finalUsername = username;
        Consumer<HttpHeaders> httpHeaders = httpHeader -> httpHeader.set(GatewayConstant.IDENTIFY_HEADER_KEY, finalUsername);
        ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders).build();
        ServerWebExchange build = exchange.mutate().request(host).build();
        log.info("GlobalTokenFilter success, user info={}", username);
        return chain.filter(exchange);

    }

}