package com.study.spring.oauth.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 自定义权限校验
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //clientId的详情配置
    @Resource
    private ClientDetailsService myJdbcClientDetailsService;
    //加载用户的信息
    @Resource
    private UserDetailsService myUserDetailsService;
    @Resource
    @Qualifier("myDaoAuthenticationProvider")
    private AuthenticationProvider myDaoAuthenticationProvider;
    @Resource
    private TokenEnhancer myTokenEnhancer;
    @Resource
    private TokenStore myTokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //设置token仓库，用户中心
        endpoints.tokenStore(myTokenStore)
                .authenticationManager(new AuthenticationManager(){
                    @Override
                    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                        return myDaoAuthenticationProvider.authenticate(authentication);
                    }
                }).userDetailsService(myUserDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
        //设置token生成策略
        endpoints.tokenEnhancer(myTokenEnhancer);
        endpoints.setClientDetailsService(myJdbcClientDetailsService);
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        MyDefaultTokenServices tokenServices = null;
        tokenServices = new OuterServiceDefaultTokenServices();
        tokenServices.setTokenStore(myTokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(myJdbcClientDetailsService);
        tokenServices.setTokenEnhancer(myTokenEnhancer);
        MyPreAuthenticatedAuthenticationProvider provider = new MyPreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(
                new MyUserDetailsByNameServiceWrapper<>(myUserDetailsService));
        tokenServices
                .setAuthenticationManager(new ProviderManager(Arrays.<AuthenticationProvider> asList(provider)));
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60000);
        // 默认30天，这里修改
        tokenServices.setRefreshTokenValiditySeconds(600000000);
        logger.info("my default token services accessTokenValiditySeconds:{} refreshTokenValiditySeconds:{}",
                1,2);
        tokenServices.setRsaProperties(null);
        return tokenServices;
    }
}
