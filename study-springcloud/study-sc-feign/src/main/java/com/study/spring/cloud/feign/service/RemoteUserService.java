package com.study.spring.cloud.feign.service;

import com.study.spring.cloud.feign.service.fallback.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(contextId = "remoteCustService", path = "/cust-info",value = "aden-cust-info", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {


    @GetMapping(value = "/inner/cust/info/fundacc/{fundAccount}")
    ResponseEntity<String> findUserInfo(@PathVariable("fundAccount") String userNo);
}
