package design.singleton;

public class SingletonInnerClass {
    private static class SingletonHandler{
        private static final SingletonInnerClass INSTANCE = new SingletonInnerClass();
    }
    private SingletonInnerClass(){}

    public static final SingletonInnerClass getInstance(){
        return SingletonHandler.INSTANCE;
    }
}
