package com.study.spring.cloud.feign;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.study.spring.cloud.feign.fallback.RemoteFundInFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(contextId = "remoteFundInService",path = "fund-in-core",value = "fund-in-core", fallbackFactory = RemoteFundInFallbackFactory.class)
public interface RemoteFundInService {
    /** 发起入金 */
    @PostMapping({"/customer/receiveNotice"})
    ResponseEntity<Void> receiveNotice(@RequestBody JSONPObject depositNotifyDTO);

    /** 分页查询入金列表 */
    @PostMapping({"/customer/queryNotices"})
    ResponseEntity<List<String>> queryNotices(@SpringQueryMap ResponseEntity queryNoticesDTO);

}
