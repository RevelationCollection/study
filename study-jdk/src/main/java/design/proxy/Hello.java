package design.proxy;

public class Hello implements HelloInterface{
    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}