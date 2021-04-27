package com.study.spring.cloud.feign.fallback;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.study.spring.cloud.feign.RemoteFundInService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemoteFundInFallbackFactory implements FallbackFactory<RemoteFundInService> {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @SuppressWarnings("unchecked")
    public RemoteFundInService create(Throwable throwable) {
        log.error("入金服务调用失败:{}", throwable.getMessage(),throwable);
        return new RemoteFundInService() {

            @Override
            public ResponseEntity<Void> receiveNotice(JSONPObject depositNotifyDTO) {
                return null;
            }

            @Override
            public ResponseEntity<List<String>> queryNotices(ResponseEntity queryNoticesDTO) {
                return null;
            }
        };
    }
}