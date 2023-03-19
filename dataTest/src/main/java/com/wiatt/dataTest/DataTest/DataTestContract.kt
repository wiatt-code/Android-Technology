package com.wiatt.dataTest.DataTest

import com.wiatt.common.mvp.BaseContract
import com.wiatt.dataTest.data.Repo

interface DataTestContract: BaseContract {
    interface IDtIModel: BaseContract.IModel {
        /**
         * 从网络请求repo数据
         */
        fun requestRepoFromNet(userName: String)

        /**
         * 从数据库获取repo数据
         */
        fun requestRepoFromDataBase()

        /**
         * 从文件读取repo数据
         */
        fun requestRepoFromFile()
    }

    interface IDtIPresenter: BaseContract.IPresenter {
        /**
         * 从网络请求repo数据
         * 在View层调用
         */
        fun getRepoFromNet(userName: String)

        /**
         * 从数据库获取repo数据
         * 在View层调用
         */
        fun getRepoFromDatabase()

        /**
         * 从文件读取repo数据
         * 在View层调用
         */
        fun getRepoFromFile()

        /**
         * 来自网络的repo数据响应
         * 在model层调用
         */
        fun responseRepoFromNet(reposData: String)

        /**
         * 来自数据库的repo数据响应
         * 在model层调用
         */
        fun responseRepoFromDataBase(repos: List<Repo>)

        /**
         * 来自文件的repo数据响应
         * 在model层调用
         */
        fun responseRepoFromFile(repos: List<Repo>)

    }

    interface IDtIView: BaseContract.IView {
        /**
         * 来自网络的repo数据响应
         */
        fun callBackRepoFromNet(reposData: String)

        /**
         * 来自数据库的repo数据响应
         */
        fun callBackRepoFromDatabase(repos: List<Repo>)

        /**
         * 来自文件的repo数据响应
         */
        fun callBackRepoFromFile(repos: List<Repo>)
    }
}