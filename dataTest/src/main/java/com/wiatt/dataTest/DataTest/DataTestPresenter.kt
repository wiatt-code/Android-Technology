package com.wiatt.dataTest.DataTest

import com.wiatt.common.mvp.BasePresenter
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
        override fun getRepoFromNet(userName: String) {
            mModel.getContract().requestRepoFromNet(userName)
        }

        override fun getRepoFromDatabase() {
            mModel.getContract().requestRepoFromDataBase()
        }

        override fun getRepoFromFile() {
            mModel.getContract().requestRepoFromFile()
        }

        override fun responseRepoFromNet(reposData: String) {
            getView()?.getContract()?.callBackRepoFromNet(reposData)
        }

        override fun responseRepoFromDataBase(repos: List<Repo>) {
            getView()?.getContract()?.callBackRepoFromDatabase(repos)
        }

        override fun responseRepoFromFile(repos: List<Repo>) {
            getView()?.getContract()?.callBackRepoFromFile(repos)
        }

    }

}