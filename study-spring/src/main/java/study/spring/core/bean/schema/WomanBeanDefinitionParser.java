package study.spring.core.bean.schema;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WomanBeanDefinitionParser extends AbstractSingleBeanDefinitionParser implements NamespaceHandler {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Woman.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String age = element.getAttribute("age");
        String weight = element.getAttribute("weight");
        if (StringUtils.hasText(weight)) {
            builder.addPropertyValue("weight", weight);
        }
        if (StringUtils.hasText(name)) {
            builder.addPropertyValue("name", name);
        }
        if (StringUtils.hasText(age)) {
            builder.addPropertyValue("age", Integer.valueOf(age));
        }
    }

    @Override
    public void init() {

    }

    @Override
    public BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder beanDefinitionHolder, ParserContext parserContext) {
        return null;
    }
}
