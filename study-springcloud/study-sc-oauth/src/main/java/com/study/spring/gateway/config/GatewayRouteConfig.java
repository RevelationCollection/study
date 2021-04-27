package com.study.spring.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//@Configuration
public class GatewayRouteConfig {

    @Value("${application.newhostroute.host:http://baidu.com/}")
    private String applicationToNewHostRouteHost;

    /**
     * 认证微服务
     * @param builder 路由设置
     * @return RouteLocator
     */
    @Bean
    public RouteLocator routeLocatorPermission(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("applicationToNewHost", r ->
//						r.path("/purge/**").or().path("/apk/**").or().path("/mes/**")
//								.or().path("/cat/**").or().path("/ccpayback/**").or().path("/mobilelr/**")
//								.or().path("/mobileapp/**").or().path("/customerapp/**").or().path("/mobilecoupons/**")
//								.or().path("/jp-communication/**").or().path("/customerAppService/**").or().path("/consplan/**")
//								.or().path("/public-gateway/**")
                                r.path("/**")
                                        .uri(applicationToNewHostRouteHost)
                )
                .build();
    }
}
