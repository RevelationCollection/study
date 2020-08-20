package design.proxy;

import java.lang.reflect.Proxy;

public class TestProxyMain {

    public static void main(String[] args) {
        HelloInterface hello = new Hello();
        ProxyHandler proxyHandler = new ProxyHandler(hello);
//        HelloInterface proxy = (HelloInterface)Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(),
//                proxyHandler);
        HelloInterface proxy = getProxy(hello);
        proxy.sayHello();
    }

    private static <T>T getProxy(T t){
        ClassLoader classLoader = t.getClass().getClassLoader();
        Class<?>[] interfaces = t.getClass().getInterfaces();
        ProxyHandler proxyHandler = new ProxyHandler(t);
        return (T) Proxy.newProxyInstance(classLoader,interfaces,proxyHandler);
    }
}
