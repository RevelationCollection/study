package com.study.spring.stream;


import com.study.spring.stream.inteface.TestOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class StreamSpringBootApplicaiton {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StreamSpringBootApplicaiton.class, args);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testSend(context);
        sendYaml(context);
    }

    private static void sendYaml(ConfigurableApplicationContext context){
        Source source = context.getBean(Source.class);
        String type = context.getEnvironment().getProperty("stream.type");
        if ("yaml".equalsIgnoreCase(type)) {
            source.output().send(new GenericMessage<>("2 测试stream"));
        }
    }

    private static void testSend(ConfigurableApplicationContext context){
        TestOutput bean = context.getBean(TestOutput.class);
        String type = context.getEnvironment().getProperty("stream.type");
        if ("interface".equalsIgnoreCase(type)) {
            bean.output().send(new GenericMessage<>("1111"));
        }
    }


}
