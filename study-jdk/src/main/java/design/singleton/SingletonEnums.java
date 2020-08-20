package design.singleton;

public class  SingletonEnums {

    //私有化构造
    private SingletonEnums() {
    }

    public static SingletonEnums getInstance() {

        return SingletonEnum.INSTANCE.getInstance();
    }

    private enum SingletonEnum {
        INSTANCE;

        private SingletonEnums singleton;

        // JVM保证这个方法绝对只调用一次
        SingletonEnum() {
            singleton = new SingletonEnums();
        }

        public SingletonEnums getInstance() {
            return singleton;
        }
    }
}
