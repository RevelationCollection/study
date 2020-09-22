package com.study.spring.cloud.zuul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("token.zuul.token-filter")
public class TokenConfiguationBean {

    private List<String> noAuthenticationRoutes;

    public List<String> getNoAuthenticationRoutes() {
        return noAuthenticationRoutes;
    }

    public void setNoAuthenticationRoutes(List<String> noAuthenticationRoutes) {
        this.noAuthenticationRoutes = noAuthenticationRoutes;
    }
}
