package com.study.spring.stream.inteface;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(InterfaceStreamCondition.class)
@Configuration
@EnableBinding(TestOutput.class)
public class InterfaceStreamConfiguration {
}
