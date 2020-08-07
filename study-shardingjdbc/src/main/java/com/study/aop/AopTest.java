package com.study.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
public class AopTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.study.service..*.*(..)) ")
    public void aopTest(){}

    @Before("aopTest()")
    public void beforMethod(JoinPoint jointPoint){
        try {
            log.info("before");
            Signature signature = jointPoint.getSignature();
//            ObjectMapper mapper = new ObjectMapper();
//            log.info(mapper.writeValueAsString(signature));
            log.info("methodName:"+signature.getName());
        } catch (Exception e) {
            log.error("error",e);
        }

    }

    @Around("aopTest()")
    public Object aroundMehtod(ProceedingJoinPoint point){
        log.info("Around start");
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            log.error("",throwable);
        }
        log.info("Around end");
        return proceed;
    }
}
