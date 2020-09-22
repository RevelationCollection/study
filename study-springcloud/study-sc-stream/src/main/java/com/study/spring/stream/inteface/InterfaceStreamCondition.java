package com.study.spring.stream.inteface;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Conditional(InterfaceStreamCondition.class)
@Component
public class InterfaceStreamCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String type = context.getEnvironment().getProperty("stream.type");
        return "interface".equalsIgnoreCase(type);
    }
}
