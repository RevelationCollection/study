package study.spring.core.bean.schema;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomSpringBean {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("custom-xsd-spring.xml");
        Woman woman = (Woman) context.getBean("woman");
        System.out.println(woman);
    }
}
