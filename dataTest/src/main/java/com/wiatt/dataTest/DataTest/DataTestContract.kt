package com.wiatt.dataTest.DataTest

import com.wiatt.common.mvp.BaseContract
import com.wiatt.dataTest.data.GithubMsg
import com.wiatt.dataTest.data.Repo

interface DataTestContract: BaseContract {
    interface IDtIModel: BaseContract.IModel {
        /**
         * 从网络请求repo数据
         */
        fun requestGithubMsgFromNet(userName: String)

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
        fun getGithubMsgFromNet(userName: String)

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
        fun responseGithubMsgFromNet(responseMsg: String)

        /**
         * 来自数据库的repo数据响应
         * 在model层调用
         */
        fun responseRepoFromDataBase(githubMsgs: MutableList<GithubMsg>)

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
        fun callBackGithubMsgFromNet(responseMsg: String)

        /**
         * 来自数据库的repo数据响应
         */
        fun callBackRepoFromDatabase(githubMsgs: MutableList<GithubMsg>)

        /**
         * 来自文件的repo数据响应
         */
        fun callBackRepoFromFile(repos: List<Repo>)
    }
}