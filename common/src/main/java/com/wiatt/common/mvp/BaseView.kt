package com.wiatt.common.mvp

/**
 * 给自己定义一个泛型，BaseView的泛型为V
 * 目的在于防止泛型循环嵌套，因为BaseView的泛型对象BasePresenter中将自己也作为了泛型
 */
interface BaseView<V: BaseView<V, P, CONTRACT_VIEW>,
        P: BasePresenter<P, V, *, *>,
        CONTRACT_VIEW> {
    /**
     * 获取契约对象
     */
    fun getContract(): CONTRACT_VIEW

    /**
     * 获取主持层对象
     */
    fun getPresenter(): P
}