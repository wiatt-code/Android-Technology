package com.wiatt.common.mvp

/**
 * 给自己定义一个泛型，BaseModel的泛型为M
 * 目的在于防止泛型循环嵌套，因为BaseModel的泛型对象BasePresenter中将自己也作为了泛型
 */
abstract class BaseModel<M: BaseModel<M, P, CONTRACT_MODEL>,
        P: BasePresenter<P, *, M, *>, CONTRACT_MODEL> constructor(open var p: P){

    public abstract fun getContract(): CONTRACT_MODEL
}