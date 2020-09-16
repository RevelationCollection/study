package com.study.spring.cloud.feign.custom.util;

import com.study.spring.cloud.feign.custom.annotation.CustomFeignGet;
import com.study.spring.cloud.feign.custom.annotation.CustomFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * 自定义注册
 * 1、扫描，扫描到我们的自定义注解的bean，获取到bean上的注解对象并获取到相应的属性配置
 * 2、为每一个接口都生成一个动态代理，通过动态代理实现的真实方法发起请求
 * 3、生成的代理对象注册到spring容器中，实现其他bean自动注入
 */
public class CustomerFeignRegister implements ImportBeanDefinitionRegistrar //注册到spring容器
                                    , EnvironmentAware //获取上下文环境
                                    , BeanClassLoaderAware //获取类加载
                                    , ResourceLoaderAware //资源
                                    , BeanFactoryAware //创建工厂
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Environment environment;

    private ClassLoader classLoader;

    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
        /*
        只有扫描到自定义注解后，才去操作
        扫描到之后需要一个classLoader去加载到我们的容器内
         */
        try {
            registetHttpRequest(registry);
        } catch (ClassNotFoundException e) {
            logger.error("error",e);
        }
    }

    private void registetHttpRequest(BeanDefinitionRegistry beanDefinitionRegistry) throws ClassNotFoundException {
        //扫描我们的类去加载
        ClassPathScanningCandidateComponentProvider classScanner = getScanner();
        classScanner.setResourceLoader(this.resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(CustomFeignClient.class);
        //过滤注解
        classScanner.addIncludeFilter(annotationTypeFilter);

        //扫描包
        String basePackage = "com.study";
        Set<BeanDefinition> beanDefinitionSet = classScanner.findCandidateComponents(basePackage);
        logger.info("CustomfeignClient size :{}", beanDefinitionSet);
        for (BeanDefinition beanDefinition : beanDefinitionSet) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                String beanClassName = beanDefinition.getBeanClassName();
                //注册
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) this.beanFactory;
                defaultListableBeanFactory.registerSingleton(beanClassName,createProxy(annotatedBeanDefinition));
            }
        }
    }

    private Object createProxy(AnnotatedBeanDefinition annotatedBeanDefinition) throws ClassNotFoundException {
        AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
        Class<?> target = Class.forName(metadata.getClassName());
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                CustomFeignClient customfeignClient = target.getAnnotation(CustomFeignClient.class);
                CustomFeignGet customFeignGet = method.getAnnotation(CustomFeignGet.class);
                String methodName = method.getName();
                if("toString".equalsIgnoreCase(methodName)){
                    return this.toString();
                }
                if ("equals".equals(methodName)){
                    return false;
                }
                if (customFeignGet != null) {
                    String url = customfeignClient.baseurl() + "/" + customFeignGet.url();
                    System.out.println("代理发起请求");
                    String result = new RestTemplate().getForObject(url, String.class, "");
                    return result;
                }
                throw new IllegalArgumentException("方法注解为空:"+method.getName());
            }
        };
        Object instance = Proxy.newProxyInstance(this.classLoader, new Class[]{target}, invocationHandler);
        return instance;
    }

    /**
     * 扫描注解过滤类
     * @return
     */
    private ClassPathScanningCandidateComponentProvider getScanner(){
        return new ClassPathScanningCandidateComponentProvider(false,this.environment){
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                //过滤注解
                if (beanDefinition.getMetadata().isInterface()) {
                    try {
                        Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), classLoader);
                        return !target.isAnnotation();
                    } catch (ClassNotFoundException e) {
                        logger.error("class not found",e);
                    }
                }
                return super.isCandidateComponent(beanDefinition);
            }
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader =classLoader;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
