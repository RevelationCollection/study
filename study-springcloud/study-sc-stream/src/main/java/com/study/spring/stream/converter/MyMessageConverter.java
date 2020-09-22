package com.study.spring.stream.converter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class MyMessageConverter extends AbstractMessageConverter {

    public MyMessageConverter() {
        super(new MimeType("application","user"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        //格式转换器
        return false;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        //消息转换成对象
        System.out.println("执行 消息to对象");
        Object payload = message.getPayload();
        //json --xml
        return new String((byte[])payload);
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        //对象转成消息
        System.out.println("执行 对象to消息");
        return super.convertToInternal(payload, headers, conversionHint);
    }
}
