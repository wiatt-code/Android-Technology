package com.wiatt.dataTest.DataTest

import com.wiatt.common.mvp.BasePresenter
import com.wiatt.dataTest.data.GithubMsg
import com.wiatt.dataTest.data.Repo

class DataTestPresenter:
        BasePresenter<DataTestPresenter, DataTestFragment, DataTestModel, DataTestContract.IDtIPresenter>() {

    override fun getContract(): DataTestContract.IDtIPresenter {
        return DtPresenter()
    }

    override fun getModel(): DataTestModel {
        return DataTestModel(this)
    }

    inner class DtPresenter: DataTestContract.IDtIPresenter {
        override fun getGithubMsgFromNet(userName: String) {
            mModel.getContract().requestGithubMsgFromNet(userName)
        }

        override fun getRepoFromDatabase() {
            mModel.getContract().requestRepoFromDataBase()
        }

        override fun getRepoFromFile() {
            mModel.getContract().requestRepoFromFile()
        }

        override fun responseGithubMsgFromNet(responseMsg: String) {
            getView()?.getContract()?.callBackGithubMsgFromNet(responseMsg)
        }

        override fun responseRepoFromDataBase(githubMsgs: MutableList<GithubMsg>) {
            getView()?.getContract()?.callBackRepoFromDatabase(githubMsgs)
        }

        override fun responseRepoFromFile(repos: List<Repo>) {
            getView()?.getContract()?.callBackRepoFromFile(repos)
        }

    }

}