package com.study.spring.cloud.feign.service.fallback;

import com.study.spring.cloud.feign.service.RemoteUserService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class RemoteUserFallbackFactory  implements FallbackFactory<RemoteUserService> {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error("入金服务调用失败:{}", throwable.getMessage(),throwable);
        return new RemoteUserService() {
            @Override
            public ResponseEntity<String> findUserInfo(String userNo) {
                return null;
            }
        };
    }
}
