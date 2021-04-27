package com.study.spring.oauth.config;

import com.study.spring.oauth.oauth.MyDaoAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Security 配置类
 */
@Slf4j
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService myuserDetailService;

    @Resource
    private PasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .oauth2Login(oauth2 -> oauth2
//                    .loginPage("/login/oauth2")
//            .authorizationEndpoint(authorization -> authorization
//            .baseUri("/login/oauth2/authorization")
//            )
//        );
        http
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll();
        log.info("security configuration has start....request /oauth/** permit all");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myDaoAuthenticationProvider());
    }

    @Bean(name="myDaoAuthenticationProvider")
    public AuthenticationProvider myDaoAuthenticationProvider() {
        MyDaoAuthenticationProvider daoAuthenticationProvider = new MyDaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myuserDetailService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(myPasswordEncoder);
        return daoAuthenticationProvider;
    }


}
