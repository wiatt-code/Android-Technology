package com.wiatt.custom_view.fragment_viewpager2

import com.wiatt.common.mvvm.BaseModel

/**
 * @author: wiatt
 * @description: 数据提供类
 */
class FragmentViewpager2Model(viewModelImpl: FragmentViewpager2ViewModel.FragmentViewpager2ViewModelImpl):
    BaseModel<
            FragmentViewpager2Contract.IFragmentViewpager2ViewModel,
            FragmentViewpager2Contract.IFragmentViewpager2Model>
        (viewModelImpl) {

    override fun getContract(): FragmentViewpager2Contract.IFragmentViewpager2Model {
        return FragmentViewpager2ModelImpl()
    }

    inner class FragmentViewpager2ModelImpl: FragmentViewpager2Contract.IFragmentViewpager2Model {

    }
}