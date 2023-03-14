package com.wiatt.simpledemo

/**
 * 饿汉式单例
 *
 * 优点：类的静态属性只有在第一次加载类的时候初始化，所以线程安全
 * 缺点：代码变得复杂，apk文件增大
 */
object SingletonKotlin_1 {
}

/**
 * 懒汉式单例
 *
 * 优点：只有在使用时才会生成对象，能够减少内存开销
 * 缺点：线程不安全，只能在单线程中使用，多个线程访问时，会产生多个对象，
 */
class SingletonKotlin_2 private constructor() {
    companion object {
        private var instance: SingletonKotlin_2? = null
            get() {
                if (field == null) {
                    field = SingletonKotlin_2()
                }
                return field
            }

        fun get(): SingletonKotlin_2 {
            return instance!!
        }
    }
}

/**
 * 线程安全的懒汉式单例
 *
 * 优点：支持多线程
 * 缺点：每次都会有一个加锁以及释放锁的操作，效率低，可以通过反射破坏单例模式。
 */
class SingletonKotlin_3 private constructor() {
    companion object {
        private var instance: SingletonKotlin_3? = null
            get() {
                if (field == null) {
                    field = SingletonKotlin_3()
                }
                return field
            }

        @Synchronized
        fun get(): SingletonKotlin_3 {
            return instance!!
        }
    }
}

/**
 * 双重校验锁式单例
 *
 * 优点：效率高，线程安全
 * 缺点：代码复杂，可以通过反射破坏单例
 */
class SingletonKotlin_4 private constructor() {
    companion object {
        val instance: SingletonKotlin_4 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SingletonKotlin_4()
        }
    }
}

/**
 * 静态内部类式单例
 *
 * 优点：类的静态属性只有在第一次加载类的时候初始化，所以线程安全
 * 缺点：代码变得复杂，apk文件增大
 */
class SingletonKotlin_5 private constructor() {
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = SingletonKotlin_5()
    }
}