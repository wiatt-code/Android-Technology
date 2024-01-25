package com.wiatt.common.mvvm

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<M: BaseModel<CONTRACT_VM, *>,
        CONTRACT_VM: BaseContract.IViewModel>: ViewModel() {

    protected var mModel: M = getModel()

    abstract fun getModel(): M
}