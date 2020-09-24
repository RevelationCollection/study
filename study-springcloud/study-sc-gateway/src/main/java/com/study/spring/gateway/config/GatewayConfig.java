package com.study.spring.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.spring.gateway.filter.CustomGlobalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Configuration
public class GatewayConfig {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public CustomGlobalFilter customGlobalFilter(){
        //自定义全局过滤器
        return new CustomGlobalFilter();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter elapsedGlobalFilter(){
        //记录请求转发的时间
        return ((exchange, chain) -> {
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            log.info(uuid +" "+"请求:{}",exchange.getRequest().getURI());
            //调用请求之前统计时间
            Long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //调用请求之后统计时间
                Long endTime = System.currentTimeMillis();
                if (null!=exchange.getResponse().getHeaders().getLocation()) {
                    String path = exchange.getResponse().getHeaders().getLocation().toString();
                    log.info(uuid +",respHeader:{},location:{}",printString(exchange.getResponse().getHeaders()),printString(path));
                    if (!StringUtils.isEmpty(path) ) {
                        log.info(uuid+",resolve{}",path);
                        try {
                            ServerHttpResponse response = exchange.getResponse();
                            response.getHeaders().setLocation(new URI(path));
                            return;
                        } catch (Exception e) {
                            log.error("error:{}",e.getMessage(),e);
                            throw new RuntimeException(e);
                        }
                    }
                }
                log.info(uuid +" "+exchange.getRequest().getURI().getRawPath()+","+
                        exchange.getResponse().getStatusCode().name()
                        + ", cost time : " + (endTime - startTime) + "ms");
            }));
        });
    }

    private String printString(Object obj){
        if (obj==null){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return  objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }


    /**
     * 自定义路由，http://127.0.0.1:9999/javabean_path/customt/add --> http://127.0.0.1:8088/customt/add
     */
    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("javaBeanCustom_roue",r -> r.path("/javabean_path/**")
                                                        .filters(f -> f.stripPrefix(1))
                                                        .uri("http://127.0.0.1:8088/")
                ).build();
    }

    /**
     spring:
     cloud:
     gateway:
     routes:
     - id: circuitbreaker_route
     uri: lb://backing-service:8088
     predicates:
     - Path=/consumingServiceEndpoint
     filters:
     - name: CircuitBreaker
     args:
     name: myCircuitBreaker
     fallbackUri: forward:/inCaseOfFailureUseThis
     statusCodes:
     - 500
     - "NOT_FOUND"
     * @param builder  降级过滤器
     * @return
     */
//    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("circuitbreaker_route", r -> r.path("/consumingServiceEndpoint")
                        .filters(f -> f.circuitBreaker(c -> c.setName("myCircuitBreaker").setFallbackUri("forward:/inCaseOfFailureUseThis").addStatusCode("INTERNAL_SERVER_ERROR"))
                                .rewritePath("/consumingServiceEndpoint", "/backingServiceEndpoint")).uri("lb://backing-service:8088")
                ).build();
    }
}
