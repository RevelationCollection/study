package com.study.spring.oauth.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class MyJdbcClientDetailsService extends JdbcClientDetailsService{


    public MyJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        log.info("load client :{}",clientId);
        // 数据库存放明文
        ClientDetails clientDetails = super.loadClientByClientId(clientId);
        // 因为createTokenRequest里会根据请求的clientId，所以要把密文放进去
        BaseClientDetails baseClientDetails = (BaseClientDetails) clientDetails;
        baseClientDetails.setClientId(clientId);
        //获取clientId对应的配置
        return clientDetails;
    }
}
