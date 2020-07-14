package study.spring.core.bean.schema;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomSpringBean {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("custom-xsd-spring.xml");
        Woman woman = (Woman) context.getBean("woman");
        System.out.println(woman);
        /*
            https://www.cnblogs.com/jifeng/archive/2011/09/14/2176599.html
            study.spring.core.bean.schema.Woman
            study.spring.core.bean.schema.MyNamespaceHandler
            study.spring.core.bean.schema.WomanBeanDefinitionParser
            src/main/resources/META-INF/spring.handlers
            src/main/resources/META-INF/spring.schemas
            src/main/resources/META-INF/woman.xsd
            src/main/resources/custom-xsd-spring.xml
         */
    }
}
