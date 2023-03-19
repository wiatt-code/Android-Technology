package com.wiatt.common.mvp

import java.lang.ref.WeakReference

// 此处写成 T: Any 而非 T，是因为会报错：'lateinit' modifier is not allowed on properties of a type with nullable upper bound
// lateinit表示延迟初始化变量，但是该变量一定不为空。而 泛型的默认父类是Any？，这是一个可以为空的类型，
// 所以要指明 T 的父类是Any，这样就必然不为空了

// abstract可以修饰 类、属性、方法；
// 抽象方法和抽象属性必须放在抽象类中
// 抽象类中可以有抽象属性或方法和非抽象的属性或方法

/**
 * 给自己定义一个泛型，BasePresenter的泛型为P
 * 目的在于防止泛型循环嵌套，因为BasePresenter的泛型对象BaseView和BaseModel中将自己也作为了泛型
 */
abstract class BasePresenter<P: BasePresenter<P, V, M, CONTRACT_PRESENTER>,
        V: BaseView<V, P, *>,
        M: BaseModel<M, P, *>,
        CONTRACT_PRESENTER>{
    private var vWeakReference: WeakReference<V>? = null
    protected var mModel: M = getModel()

    fun bindView(v: V) {
        vWeakReference = WeakReference(v)
    }

    fun unBindView() {
        if (vWeakReference != null) {
            vWeakReference!!.clear()
            vWeakReference = null
            System.gc()
        }
    }

    fun getView(): V? {
        if (vWeakReference != null) {
            return vWeakReference!!.get()
        }
        return null
    }

    abstract fun getContract(): CONTRACT_PRESENTER

    abstract fun getModel(): M
}