package com.study.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.study.spring.cloud.zuul.config.TokenConfiguationBean;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthTokenFilter extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private TokenConfiguationBean tokenConfiguationBean;

    @Value("${token.test-custom}")
    private String customToken;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 15;
    }

    @Override
    public boolean shouldFilter() {
        //过滤url，如果是登录请求路径，不做拦截
        RequestContext context = RequestContext.getCurrentContext();
        return !tokenConfiguationBean.getNoAuthenticationRoutes().contains(context.get("proxy"));
    }

    @Override
    public Object run()  {
        //校验token
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("Authorization");
        log.info("token :{}",token);
        if (token==null){
            rejectRquest();
            return null;
        }
        if (!customToken.equals(token)) {
            rejectRquest();
            return null;
        }
        return null;
    }

    private void rejectRquest(){
        RequestContext.getCurrentContext().setResponseStatusCode(HttpStatus.SC_FORBIDDEN);
        ReflectionUtils.rethrowRuntimeException(new ZuulException("无范围权限",HttpStatus.SC_FORBIDDEN,"token"));
    }
}
