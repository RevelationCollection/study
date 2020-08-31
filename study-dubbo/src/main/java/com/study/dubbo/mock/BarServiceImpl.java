package com.study.dubbo.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarServiceImpl implements BarService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String testMockFunction(String param) {
        logger.info("mock paran:{}",param);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            logger.error("error",e);
        }
        logger.info("sleep end");
        return "success real return";
    }
}
