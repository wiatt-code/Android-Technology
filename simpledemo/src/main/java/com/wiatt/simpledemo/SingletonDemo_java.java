package com.wiatt.simpledemo;

/**
 * 饿汉式单例
 *
 * 优点：简单方便，线程安全
 * 缺点：无论是否用到，都会进行实例化，而且在类加载时就会实例化
 */
class SingletonJava_1 {
    private static SingletonJava_1 instance = new SingletonJava_1();
    private SingletonJava_1() {}

    public static SingletonJava_1 getInstance() {
        return instance;
    }
}

/**
 * 懒汉式单例
 *
 * 优点：只有在使用时才会生成对象，能够减少内存开销
 * 缺点：线程不安全，只能在单线程中使用，多个线程访问时，会产生多个对象，
 */
class SingletonJava_2 {
    private static SingletonJava_2 instance;
    private SingletonJava_2() {}

    public static SingletonJava_2 getInstance() {
        if (instance == null) {
            instance = new SingletonJava_2();
        }
        return instance;
    }
}

/**
 * 线程安全的懒汉式单例
 *
 * 优点：支持多线程
 * 缺点：每次都会有一个加锁以及释放锁的操作，效率低，可以通过反射破坏单例模式。
 */
class SingletonJava_3 {
    private static SingletonJava_3 instance;
    private SingletonJava_3() {}

    public static synchronized SingletonJava_3 getInstance() {
        synchronized (SingletonJava_3.class) {
            if (instance == null) {
                instance = new SingletonJava_3();
            }
        }
        return instance;
    }
}

/**
 * 双重校验锁式单例
 *
 * 优点：效率高，线程安全
 * 缺点：代码复杂，可以通过反射破坏单例
 */
class SingletonJava_4 {
    private volatile static SingletonJava_4 instance;
    private SingletonJava_4() {}

    private static SingletonJava_4 getInstance() {
        if (instance == null) {
            synchronized (SingletonJava_4.class) {
                if (instance == null) {
                    instance = new SingletonJava_4();
                }
            }
        }
        return instance;
    }
}

/**
 * 静态内部类式单例
 *
 * 优点：类的静态属性只有在第一次加载类的时候初始化，所以线程安全
 * 缺点：代码变得复杂，apk文件增大
 */
class SingletonJava_5 {
    private static class SingletonHolder {
        private static SingletonJava_5 instance = new SingletonJava_5();
    }
    private SingletonJava_5() {}

    public static SingletonJava_5 getInstance() {
        return SingletonHolder.instance;
    }
}


