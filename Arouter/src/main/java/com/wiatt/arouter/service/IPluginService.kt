package com.wiatt.arouter.service

import com.alibaba.android.arouter.facade.template.IProvider

interface IPluginService: IProvider{
    fun getBacklogCount(): Int

    fun getSendingCount(): Int

    fun getEvaluatingCount(): Int
}