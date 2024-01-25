package com.wiatt.common.mvvm

abstract class BaseModel<CONTRACT_VM: BaseContract.IViewModel,
        CONTRACT_M: BaseContract.IModel> constructor(var listener: CONTRACT_VM) {

    abstract fun getContract(): CONTRACT_M
}