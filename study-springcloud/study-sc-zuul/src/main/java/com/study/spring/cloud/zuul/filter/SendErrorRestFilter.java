package com.study.spring.cloud.zuul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendErrorRestFilter extends SendErrorFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        Throwable throwable = context.getThrowable();
        ExceptionHolder zuulException = findZuulException(context.getThrowable());
        int statusCode = zuulException.getStatusCode();
        int responseStatusCode = context.getResponseStatusCode();
        if (HttpStatus.SC_FORBIDDEN!=responseStatusCode){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> response = new HashMap<>();
        response.put("code","异常码："+statusCode);
        response.put("msg","权限不足");
        logger.error("custom error",zuulException);
        try {
            String res = objectMapper.writeValueAsString(response);
            context.setResponseBody(res);
            context.getResponse().setContentType("application/json;charset="+StandardCharsets.UTF_8);
            context.remove("throwable");
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
