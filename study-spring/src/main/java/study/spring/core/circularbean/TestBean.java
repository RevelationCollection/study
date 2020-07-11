package study.spring.core.circularbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {
    public static void main(String[] args) {
        //bean 循环依赖 不支持构造
        ApplicationContext context = new ClassPathXmlApplicationContext("constructor-spring.xml");
        //set 单例 循环依赖 注入
//        ApplicationContext context = new ClassPathXmlApplicationContext("singleton-spring.xml");
        //非单例，原型模式bean
//        ApplicationContext context = new ClassPathXmlApplicationContext("prototype-spring.xml");

        Object c = context.getBean("c");
        boolean isSame = c  instanceof StudyC;
        System.out.println(isSame);

    }
}
