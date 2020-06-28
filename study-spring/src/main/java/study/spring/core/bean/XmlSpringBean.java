package study.spring.core.bean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlSpringBean {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("simple-init.simple.xml");
    }
}
