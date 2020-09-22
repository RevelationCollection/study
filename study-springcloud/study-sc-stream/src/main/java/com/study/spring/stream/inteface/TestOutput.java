package com.study.spring.stream.inteface;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TestOutput{
    @Output("testOutput")
    MessageChannel output();
}