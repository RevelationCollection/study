package study.spring.core.bean.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("woman",new WomanBeanDefinitionParser());
    }
}
