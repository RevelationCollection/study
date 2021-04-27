package com.study.spring.gateway.filter;

import com.baomidou.mybatisplus.extension.api.R;
import com.study.spring.oauth.util.JSONUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 黑名单过滤器
 */
@Component
public class BlackListUrlFilter extends AbstractGatewayFilterFactory<BlackListUrlFilter.Config> {
	final static R result = R.failed( "服务拒绝访问");
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
        	
            String url = exchange.getRequest().getURI().getPath();
            if (config.matchBlacklist(url)) {
                ServerHttpResponse response = exchange.getResponse();
                return exchange.getResponse().writeWith(
                        Mono.just(response.bufferFactory().wrap(JSONUtils.toString(result).getBytes())));
            }

            return chain.filter(exchange);
        };
    }

    public BlackListUrlFilter() {
        super(Config.class);
    }

    public static class Config {
        private List<String> blacklistUrl;

        private List<Pattern> blacklistUrlPattern = new ArrayList<>();

        public boolean matchBlacklist(String url) {
            return blacklistUrlPattern.isEmpty() ? false : blacklistUrlPattern.stream().filter(p -> p.matcher(url).find()).findAny().isPresent();
        }

        public List<String> getBlacklistUrl() {
            return blacklistUrl;
        }

        public void setBlacklistUrl(List<String> blacklistUrl) {
            this.blacklistUrl = blacklistUrl;
            this.blacklistUrlPattern.clear();
            this.blacklistUrl.forEach(url -> {
                this.blacklistUrlPattern.add(Pattern.compile(url.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE));
            });
        }
    }

}