package com.study.spring.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "rsa")
public class RsaProperties {

    // 公钥
    private String publicKey;
    // 私钥
    private String privateKey;

}